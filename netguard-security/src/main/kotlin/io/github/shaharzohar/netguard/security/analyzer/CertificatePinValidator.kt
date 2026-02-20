package io.github.shaharzohar.netguard.security.analyzer

import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.security.models.PinValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.cert.X509Certificate
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Validates certificate pins against configured SHA-256 fingerprints.
 *
 * Certificate pinning helps prevent man-in-the-middle attacks by verifying
 * that the server's certificate matches a known fingerprint.
 *
 * @since 1.1.0
 */
internal class CertificatePinValidator {

    /**
     * Validate the certificate for [hostname] against the provided [pins].
     *
     * @param hostname Target hostname
     * @param port Target port (default 443)
     * @param pins Set of SHA-256 fingerprints (format: "AA:BB:CC:...")
     * @return [PinValidationResult] indicating match status
     */
    suspend fun validate(
        hostname: String,
        port: Int = 443,
        pins: Set<String>
    ): PinValidationResult = withContext(Dispatchers.IO) {
        if (pins.isEmpty()) {
            return@withContext PinValidationResult.NoPinsConfigured
        }

        try {
            val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
            val socket = factory.createSocket(hostname, port) as SSLSocket

            socket.use { sslSocket ->
                sslSocket.soTimeout = TIMEOUT_MS
                sslSocket.startHandshake()

                val certs = sslSocket.session.peerCertificates
                val normalizedPins = pins.map { it.uppercase() }.toSet()

                // Check each certificate in the chain against configured pins
                for (cert in certs) {
                    if (cert is X509Certificate) {
                        val fingerprint = sha256Fingerprint(cert)
                        if (fingerprint in normalizedPins) {
                            return@withContext PinValidationResult.Trusted(
                                matchedPin = fingerprint,
                                certificateFingerprint = fingerprint
                            )
                        }
                    }
                }

                // No match found
                val leafFingerprint = (certs.firstOrNull() as? X509Certificate)
                    ?.let { sha256Fingerprint(it) } ?: "unknown"

                PinValidationResult.PinMismatch(
                    expectedPins = normalizedPins.toList(),
                    actualFingerprint = leafFingerprint
                )
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "Pin validation failed for $hostname", e)
            PinValidationResult.Error("Pin validation failed: ${e.message}")
        }
    }

    private fun sha256Fingerprint(cert: X509Certificate): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(cert.encoded)
        return digest.joinToString(":") { "%02X".format(it) }
    }

    companion object {
        private const val TAG = "CertPinValidator"
        private const val TIMEOUT_MS = 10_000
    }
}
