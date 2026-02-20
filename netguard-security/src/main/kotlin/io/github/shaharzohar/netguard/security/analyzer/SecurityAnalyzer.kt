package io.github.shaharzohar.netguard.security.analyzer

import android.content.Context
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.security.models.CertificateTransparencyResult
import io.github.shaharzohar.netguard.security.models.MixedContentWarning
import io.github.shaharzohar.netguard.security.models.PinValidationResult
import io.github.shaharzohar.netguard.security.models.RiskLevel
import io.github.shaharzohar.netguard.security.models.SecurityReport
import io.github.shaharzohar.netguard.security.models.SecurityWarning
import io.github.shaharzohar.netguard.security.models.SecurityWarning.Severity
import io.github.shaharzohar.netguard.security.models.TlsAnalysisResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import okhttp3.Interceptor

/**
 * Main entry point for network security analysis.
 *
 * Combines TLS analysis, Certificate Transparency checking, certificate pin
 * validation, and mixed content detection into a comprehensive security toolkit.
 *
 * ## Usage
 *
 * ```kotlin
 * val analyzer = SecurityAnalyzer(context)
 *
 * // Full security report
 * val report = analyzer.analyze("example.com")
 * println("Risk: ${report.overallRisk}")
 * report.warnings.forEach { println("${it.severity}: ${it.message}") }
 *
 * // TLS-only analysis
 * val tls = analyzer.analyzeTls("example.com")
 * println("TLS: ${tls.tlsVersion}, Cipher: ${tls.cipherSuite}")
 *
 * // Mixed content interceptor for OkHttp
 * val client = OkHttpClient.Builder()
 *     .addInterceptor(analyzer.createMixedContentInterceptor())
 *     .build()
 * ```
 *
 * @since 1.1.0
 */
class SecurityAnalyzer(context: Context) {

    private val tlsAnalyzer = TlsAnalyzer()
    private val ctChecker = CertificateTransparencyChecker()
    private val pinValidator = CertificatePinValidator()
    private val mixedContentDetector = MixedContentDetector()

    private var configuredPins: Map<String, Set<String>> = emptyMap()

    /**
     * Configure certificate pins for hostname validation.
     *
     * @param pins Map of hostname to set of SHA-256 fingerprints
     */
    fun configurePins(pins: Map<String, Set<String>>) {
        configuredPins = pins
    }

    /**
     * Perform a comprehensive security analysis of a host.
     *
     * Runs TLS analysis, Certificate Transparency check, and pin validation
     * in sequence, then aggregates results into a [SecurityReport].
     *
     * @param hostname Target hostname
     * @param port Target port (default 443)
     * @return [SecurityReport] with all findings
     */
    suspend fun analyze(hostname: String, port: Int = 443): SecurityReport {
        val tlsResult = tlsAnalyzer.analyze(hostname, port)
        val ctResult = ctChecker.check(hostname, port)
        val pinResult = validatePins(hostname, port)

        val allWarnings = mutableListOf<SecurityWarning>()
        allWarnings.addAll(tlsResult.warnings)

        // CT warnings
        when (ctResult) {
            is CertificateTransparencyResult.Invalid -> {
                allWarnings.add(
                    SecurityWarning(
                        severity = Severity.MEDIUM,
                        code = "NO_CT",
                        message = "No Certificate Transparency SCTs found for $hostname",
                        recommendation = "Ensure CA provides SCT-embedded certificates"
                    )
                )
            }
            is CertificateTransparencyResult.Error -> {
                allWarnings.add(
                    SecurityWarning(
                        severity = Severity.LOW,
                        code = "CT_CHECK_ERROR",
                        message = "Certificate Transparency check failed: ${ctResult.message}",
                        recommendation = "Verify host connectivity and try again"
                    )
                )
            }
            is CertificateTransparencyResult.Valid -> { /* No warning needed */ }
        }

        // Pin warnings
        when (pinResult) {
            is PinValidationResult.PinMismatch -> {
                allWarnings.add(
                    SecurityWarning(
                        severity = Severity.CRITICAL,
                        code = "PIN_MISMATCH",
                        message = "Certificate pin mismatch for $hostname",
                        recommendation = "Potential MITM attack or certificate rotation — verify with server admin"
                    )
                )
            }
            is PinValidationResult.Error -> {
                allWarnings.add(
                    SecurityWarning(
                        severity = Severity.LOW,
                        code = "PIN_CHECK_ERROR",
                        message = "Pin validation failed: ${pinResult.message}",
                        recommendation = "Verify host connectivity and try again"
                    )
                )
            }
            is PinValidationResult.Trusted, is PinValidationResult.NoPinsConfigured -> {
                /* No warning needed */
            }
        }

        val overallRisk = calculateRisk(allWarnings)

        return SecurityReport(
            hostname = hostname,
            port = port,
            tlsResult = tlsResult,
            ctResult = ctResult,
            pinResult = pinResult,
            mixedContentWarnings = emptyList(), // Populated via interceptor at runtime
            overallRisk = overallRisk,
            warnings = allWarnings
        )
    }

    /**
     * Analyze only the TLS/SSL configuration of a host.
     *
     * @param hostname Target hostname
     * @param port Target port (default 443)
     * @return [TlsAnalysisResult] with protocol, cipher, and certificate details
     */
    suspend fun analyzeTls(
        hostname: String,
        port: Int = 443
    ): TlsAnalysisResult = tlsAnalyzer.analyze(hostname, port)

    /**
     * Check Certificate Transparency compliance for a host.
     *
     * @param hostname Target hostname
     * @return [CertificateTransparencyResult] with SCT presence info
     */
    suspend fun checkCertificateTransparency(
        hostname: String
    ): CertificateTransparencyResult = ctChecker.check(hostname)

    /**
     * Validate certificate pins for a host.
     *
     * Uses pins configured via [configurePins] for the given hostname.
     *
     * @param hostname Target hostname
     * @param port Target port (default 443)
     * @return [PinValidationResult] with validation status
     */
    suspend fun validatePins(
        hostname: String,
        port: Int = 443
    ): PinValidationResult {
        val pins = configuredPins[hostname]
            ?: return PinValidationResult.NoPinsConfigured
        return pinValidator.validate(hostname, port, pins)
    }

    /**
     * Creates an OkHttp [Interceptor] that detects mixed content in HTTPS HTML responses.
     *
     * Add this interceptor to your OkHttpClient to monitor for insecure resource loading.
     *
     * @return OkHttp Interceptor for mixed content detection
     */
    fun createMixedContentInterceptor(): Interceptor = mixedContentDetector.createInterceptor()

    /**
     * Observe security warnings from the mixed content interceptor in real-time.
     *
     * @return Flow of security warnings
     */
    fun observeSecurityWarnings(): Flow<SecurityWarning> = mixedContentDetector.warnings

    private fun calculateRisk(warnings: List<SecurityWarning>): RiskLevel {
        if (warnings.isEmpty()) return RiskLevel.NONE

        val maxSeverity = warnings.maxOf { it.severity }
        return when (maxSeverity) {
            Severity.CRITICAL -> RiskLevel.CRITICAL
            Severity.HIGH -> RiskLevel.HIGH
            Severity.MEDIUM -> RiskLevel.MEDIUM
            Severity.LOW -> RiskLevel.LOW
            Severity.INFO -> RiskLevel.NONE
        }
    }

    companion object {
        private const val TAG = "SecurityAnalyzer"
    }
}
