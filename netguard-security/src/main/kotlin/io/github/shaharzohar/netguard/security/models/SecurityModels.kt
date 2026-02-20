package io.github.shaharzohar.netguard.security.models

/**
 * Aggregate security report for a host.
 *
 * @since 1.1.0
 */
data class SecurityReport(
    val hostname: String,
    val port: Int,
    val tlsResult: TlsAnalysisResult,
    val ctResult: CertificateTransparencyResult,
    val pinResult: PinValidationResult,
    val mixedContentWarnings: List<MixedContentWarning>,
    val overallRisk: RiskLevel,
    val warnings: List<SecurityWarning>,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Result of TLS/SSL connection analysis.
 *
 * @since 1.1.0
 */
data class TlsAnalysisResult(
    val tlsVersion: String,
    val cipherSuite: String,
    val isWeakCipher: Boolean,
    val isDeprecatedProtocol: Boolean,
    val certificateChain: List<CertificateInfo>,
    val warnings: List<SecurityWarning>
)

/**
 * Information about an X.509 certificate in the chain.
 *
 * @since 1.1.0
 */
data class CertificateInfo(
    val subject: String,
    val issuer: String,
    val notBefore: Long,
    val notAfter: Long,
    val serialNumber: String,
    val signatureAlgorithm: String,
    val fingerprint: String,
    val keySize: Int,
    val isExpired: Boolean,
    val isExpiringSoon: Boolean
)

/**
 * Result of Certificate Transparency verification.
 *
 * @since 1.1.0
 */
sealed class CertificateTransparencyResult {

    /**
     * Certificate has valid SCT (Signed Certificate Timestamp) extension.
     */
    data class Valid(val sctCount: Int) : CertificateTransparencyResult()

    /**
     * No SCT extension found in the certificate.
     */
    data object Invalid : CertificateTransparencyResult()

    /**
     * CT check could not be completed.
     */
    data class Error(val message: String) : CertificateTransparencyResult()
}

/**
 * Result of certificate pin validation.
 *
 * @since 1.1.0
 */
sealed class PinValidationResult {

    /**
     * Certificate pin matches a configured pin.
     */
    data class Trusted(
        val matchedPin: String,
        val certificateFingerprint: String
    ) : PinValidationResult()

    /**
     * Certificate pin does not match any configured pin.
     */
    data class PinMismatch(
        val expectedPins: List<String>,
        val actualFingerprint: String
    ) : PinValidationResult()

    /**
     * No pins are configured for the hostname.
     */
    data object NoPinsConfigured : PinValidationResult()

    /**
     * Pin validation could not be completed.
     */
    data class Error(val message: String) : PinValidationResult()
}

/**
 * Warning about mixed content (HTTP resource loaded from HTTPS page).
 *
 * @since 1.1.0
 */
data class MixedContentWarning(
    val parentUrl: String,
    val insecureResourceUrl: String,
    val resourceType: String
)

/**
 * Security warning with severity and recommendation.
 *
 * @since 1.1.0
 */
data class SecurityWarning(
    val severity: Severity,
    val code: String,
    val message: String,
    val recommendation: String
) {
    /**
     * Warning severity levels.
     */
    enum class Severity {
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW,
        INFO
    }
}

/**
 * Overall risk level for a security assessment.
 *
 * @since 1.1.0
 */
enum class RiskLevel {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    NONE
}
