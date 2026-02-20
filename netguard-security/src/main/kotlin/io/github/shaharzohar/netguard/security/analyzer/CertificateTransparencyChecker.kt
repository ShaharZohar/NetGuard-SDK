package io.github.shaharzohar.netguard.security.analyzer

import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.security.models.CertificateTransparencyResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.cert.X509Certificate
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Checks for Certificate Transparency (CT) compliance by looking for
 * Signed Certificate Timestamp (SCT) extensions in certificates.
 *
 * CT helps detect mis-issued certificates by requiring CAs to log
 * all certificates to publicly auditable logs.
 *
 * @since 1.1.0
 */
internal class CertificateTransparencyChecker {

    /**
     * Check if the certificate for [hostname] includes SCT extensions.
     */
    suspend fun check(
        hostname: String,
        port: Int = 443
    ): CertificateTransparencyResult = withContext(Dispatchers.IO) {
        try {
            val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
            val socket = factory.createSocket(hostname, port) as SSLSocket

            socket.use { sslSocket ->
                sslSocket.soTimeout = TIMEOUT_MS
                sslSocket.startHandshake()

                val certs = sslSocket.session.peerCertificates
                val leafCert = certs.firstOrNull() as? X509Certificate
                    ?: return@withContext CertificateTransparencyResult.Error("No leaf certificate found")

                // Check for SCT extension (OID 1.3.6.1.4.1.11129.2.4.2)
                val sctExtension = leafCert.getExtensionValue(SCT_EXTENSION_OID)

                if (sctExtension != null && sctExtension.isNotEmpty()) {
                    // Count SCTs from the extension data
                    val sctCount = estimateSctCount(sctExtension)
                    CertificateTransparencyResult.Valid(sctCount = sctCount)
                } else {
                    CertificateTransparencyResult.Invalid
                }
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "CT check failed for $hostname", e)
            CertificateTransparencyResult.Error("CT check failed: ${e.message}")
        }
    }

    /**
     * Estimate the number of SCTs from the extension value.
     *
     * The SCT list is a TLS-encoded list. We do a rough count
     * by parsing the outer structure.
     */
    private fun estimateSctCount(extensionValue: ByteArray): Int {
        if (extensionValue.size < 6) return 1

        return try {
            // Skip the outer OCTET STRING wrapper (DER: tag + length)
            var offset = 2
            if (extensionValue[1].toInt() and 0x80 != 0) {
                offset += (extensionValue[1].toInt() and 0x7F)
            }

            // The SCT list starts with a 2-byte total length
            if (offset + 2 > extensionValue.size) return 1
            val listLen = ((extensionValue[offset].toInt() and 0xFF) shl 8) or
                    (extensionValue[offset + 1].toInt() and 0xFF)
            offset += 2

            var count = 0
            val endOffset = offset + listLen
            while (offset + 2 < endOffset && offset + 2 < extensionValue.size) {
                val sctLen = ((extensionValue[offset].toInt() and 0xFF) shl 8) or
                        (extensionValue[offset + 1].toInt() and 0xFF)
                offset += 2 + sctLen
                count++
            }
            if (count == 0) 1 else count
        } catch (e: Exception) {
            1
        }
    }

    companion object {
        private const val TAG = "CTChecker"
        private const val TIMEOUT_MS = 10_000
        /** OID for SCT List extension */
        private const val SCT_EXTENSION_OID = "1.3.6.1.4.1.11129.2.4.2"
    }
}
