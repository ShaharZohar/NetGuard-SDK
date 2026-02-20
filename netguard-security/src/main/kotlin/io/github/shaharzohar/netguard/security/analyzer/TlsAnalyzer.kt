package io.github.shaharzohar.netguard.security.analyzer

import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.security.models.CertificateInfo
import io.github.shaharzohar.netguard.security.models.SecurityWarning
import io.github.shaharzohar.netguard.security.models.SecurityWarning.Severity
import io.github.shaharzohar.netguard.security.models.TlsAnalysisResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Analyzes TLS/SSL configuration of a remote host.
 *
 * Performs a TLS handshake and inspects the negotiated protocol,
 * cipher suite, and certificate chain for security issues.
 *
 * @since 1.1.0
 */
internal class TlsAnalyzer {

    /**
     * Analyze TLS configuration for a hostname.
     */
    suspend fun analyze(
        hostname: String,
        port: Int = 443
    ): TlsAnalysisResult = withContext(Dispatchers.IO) {
        try {
            val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
            val socket = factory.createSocket(hostname, port) as SSLSocket

            socket.use { sslSocket ->
                sslSocket.soTimeout = TIMEOUT_MS
                sslSocket.startHandshake()

                val session = sslSocket.session
                val protocol = session.protocol
                val cipherSuite = session.cipherSuite
                val peerCerts = session.peerCertificates

                val certChain = peerCerts.filterIsInstance<X509Certificate>()
                    .map { it.toCertificateInfo() }

                val isWeakCipher = isWeakCipher(cipherSuite)
                val isDeprecated = isDeprecatedProtocol(protocol)
                val warnings = buildWarnings(protocol, cipherSuite, certChain, isWeakCipher, isDeprecated)

                TlsAnalysisResult(
                    tlsVersion = protocol,
                    cipherSuite = cipherSuite,
                    isWeakCipher = isWeakCipher,
                    isDeprecatedProtocol = isDeprecated,
                    certificateChain = certChain,
                    warnings = warnings
                )
            }
        } catch (e: Exception) {
            NetGuard.logger.e(TAG, "TLS analysis failed for $hostname:$port", e)
            TlsAnalysisResult(
                tlsVersion = "unknown",
                cipherSuite = "unknown",
                isWeakCipher = false,
                isDeprecatedProtocol = false,
                certificateChain = emptyList(),
                warnings = listOf(
                    SecurityWarning(
                        severity = Severity.HIGH,
                        code = "TLS_HANDSHAKE_FAILED",
                        message = "TLS handshake failed: ${e.message}",
                        recommendation = "Verify the host is reachable and supports TLS"
                    )
                )
            )
        }
    }

    private fun isWeakCipher(cipherSuite: String): Boolean {
        val weak = cipherSuite.uppercase()
        return WEAK_CIPHERS.any { weak.contains(it) }
    }

    private fun isDeprecatedProtocol(protocol: String): Boolean {
        return protocol in DEPRECATED_PROTOCOLS
    }

    private fun buildWarnings(
        protocol: String,
        cipherSuite: String,
        certChain: List<CertificateInfo>,
        isWeakCipher: Boolean,
        isDeprecated: Boolean
    ): List<SecurityWarning> {
        val warnings = mutableListOf<SecurityWarning>()

        if (isDeprecated) {
            warnings.add(
                SecurityWarning(
                    severity = Severity.CRITICAL,
                    code = "DEPRECATED_PROTOCOL",
                    message = "Server uses deprecated protocol: $protocol",
                    recommendation = "Upgrade to TLS 1.2 or TLS 1.3"
                )
            )
        }

        if (isWeakCipher) {
            warnings.add(
                SecurityWarning(
                    severity = Severity.HIGH,
                    code = "WEAK_CIPHER",
                    message = "Weak cipher suite negotiated: $cipherSuite",
                    recommendation = "Configure server to prefer strong cipher suites (AES-GCM, ChaCha20)"
                )
            )
        }

        certChain.forEach { cert ->
            if (cert.isExpired) {
                warnings.add(
                    SecurityWarning(
                        severity = Severity.CRITICAL,
                        code = "CERT_EXPIRED",
                        message = "Certificate expired: ${cert.subject}",
                        recommendation = "Renew the certificate immediately"
                    )
                )
            } else if (cert.isExpiringSoon) {
                warnings.add(
                    SecurityWarning(
                        severity = Severity.MEDIUM,
                        code = "CERT_EXPIRING_SOON",
                        message = "Certificate expiring soon: ${cert.subject}",
                        recommendation = "Plan certificate renewal within 30 days"
                    )
                )
            }

            if (cert.keySize < MIN_RSA_KEY_SIZE && cert.signatureAlgorithm.contains("RSA", ignoreCase = true)) {
                warnings.add(
                    SecurityWarning(
                        severity = Severity.HIGH,
                        code = "WEAK_KEY_SIZE",
                        message = "Weak RSA key size (${cert.keySize} bits) for: ${cert.subject}",
                        recommendation = "Use at least 2048-bit RSA keys"
                    )
                )
            }

            if (cert.signatureAlgorithm.contains("SHA1", ignoreCase = true) ||
                cert.signatureAlgorithm.contains("MD5", ignoreCase = true)
            ) {
                warnings.add(
                    SecurityWarning(
                        severity = Severity.HIGH,
                        code = "WEAK_SIGNATURE",
                        message = "Weak signature algorithm: ${cert.signatureAlgorithm}",
                        recommendation = "Use SHA-256 or stronger signature algorithm"
                    )
                )
            }
        }

        return warnings
    }

    private fun X509Certificate.toCertificateInfo(): CertificateInfo {
        val now = System.currentTimeMillis()
        val expiryMs = notAfter.time
        val thirtyDaysMs = TimeUnit.DAYS.toMillis(30)

        return CertificateInfo(
            subject = subjectX500Principal.name,
            issuer = issuerX500Principal.name,
            notBefore = notBefore.time,
            notAfter = expiryMs,
            serialNumber = serialNumber.toString(16),
            signatureAlgorithm = sigAlgName,
            fingerprint = sha256Fingerprint(),
            keySize = publicKey.encoded.size * 8,
            isExpired = now > expiryMs,
            isExpiringSoon = !isExpired(now, expiryMs) && (expiryMs - now) < thirtyDaysMs
        )
    }

    private fun isExpired(now: Long, expiryMs: Long): Boolean = now > expiryMs

    private fun X509Certificate.sha256Fingerprint(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(encoded)
        return digest.joinToString(":") { "%02X".format(it) }
    }

    companion object {
        private const val TAG = "TlsAnalyzer"
        private const val TIMEOUT_MS = 10_000
        private const val MIN_RSA_KEY_SIZE = 2048

        private val WEAK_CIPHERS = listOf(
            "RC4", "3DES", "DES", "EXPORT", "NULL", "ANON", "MD5"
        )

        private val DEPRECATED_PROTOCOLS = setOf(
            "SSLv2", "SSLv3", "TLSv1", "TLSv1.1"
        )
    }
}
