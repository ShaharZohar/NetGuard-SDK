package io.github.shaharzohar.netguard.sample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import io.github.shaharzohar.netguard.captiveportal.detector.CaptivePortalDetector
import io.github.shaharzohar.netguard.captiveportal.dns.DnsHijackDetector
import io.github.shaharzohar.netguard.captiveportal.models.CaptivePortalState
import io.github.shaharzohar.netguard.captiveportal.models.DnsHijackResult
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.core.vpn.DnsLeakResult
import io.github.shaharzohar.netguard.core.vpn.SplitTunnelingResult
import io.github.shaharzohar.netguard.core.vpn.VpnDetector
import io.github.shaharzohar.netguard.core.vpn.VpnState
import io.github.shaharzohar.netguard.okhttp.addNetGuardInterceptor
import io.github.shaharzohar.netguard.sample.databinding.ActivityMainBinding
import io.github.shaharzohar.netguard.security.analyzer.SecurityAnalyzer
import io.github.shaharzohar.netguard.security.models.CertificateTransparencyResult
import io.github.shaharzohar.netguard.security.models.RiskLevel
import io.github.shaharzohar.netguard.traffic.export.HarShareHelper
import io.github.shaharzohar.netguard.traffic.ui.TrafficLogActivity
import io.github.shaharzohar.netguard.wifi.monitor.WifiMonitor
import io.github.shaharzohar.netguard.wifi.quality.ConnectionQualityMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Main activity demonstrating NetGuard SDK features.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var captivePortalDetector: CaptivePortalDetector
    private lateinit var dnsHijackDetector: DnsHijackDetector
    private lateinit var wifiMonitor: WifiMonitor
    private lateinit var httpClient: OkHttpClient
    private lateinit var vpnDetector: VpnDetector
    private lateinit var qualityMonitor: ConnectionQualityMonitor
    private lateinit var harShareHelper: HarShareHelper
    private lateinit var securityAnalyzer: SecurityAnalyzer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSdk()
        setupUI()
        requestPermissions()
    }

    private fun initializeSdk() {
        captivePortalDetector = CaptivePortalDetector(this)
        dnsHijackDetector = DnsHijackDetector()
        wifiMonitor = WifiMonitor(this)
        vpnDetector = VpnDetector(this)
        qualityMonitor = ConnectionQualityMonitor(this)
        harShareHelper = HarShareHelper(this)
        securityAnalyzer = SecurityAnalyzer(this)

        // Create OkHttpClient with NetGuard interceptor
        httpClient = OkHttpClient.Builder()
            .addNetGuardInterceptor()
            .build()
    }

    private fun setupUI() {
        binding.apply {
            sdkVersionText.text = "NetGuard SDK v${NetGuard.VERSION}"

            // Captive Portal Detection
            checkCaptivePortalButton.setOnClickListener {
                checkCaptivePortal()
            }

            // DNS Hijack Detection
            checkDnsHijackButton.setOnClickListener {
                checkDnsHijack()
            }

            // Make HTTP Request
            makeRequestButton.setOnClickListener {
                makeTestRequest()
            }

            // View Traffic Logs
            viewTrafficLogsButton.setOnClickListener {
                TrafficLogActivity.start(this@MainActivity)
            }

            // Export HAR
            exportHarButton.setOnClickListener {
                exportHar()
            }

            // Check WiFi Info
            checkWifiButton.setOnClickListener {
                checkWifiInfo()
            }

            // Measure Latency
            measureLatencyButton.setOnClickListener {
                measureLatency()
            }

            // Connection Quality
            connectionQualityButton.setOnClickListener {
                checkConnectionQuality()
            }

            // Check VPN
            checkVpnButton.setOnClickListener {
                checkVpn()
            }

            // Security Scan
            securityScanButton.setOnClickListener {
                securityScan()
            }
        }
    }

    private fun checkCaptivePortal() {
        binding.statusText.text = "Checking for captive portal..."

        lifecycleScope.launch {
            val state = captivePortalDetector.checkNow()
            binding.statusText.text = when (state) {
                is CaptivePortalState.Clear -> "No captive portal detected"
                is CaptivePortalState.Detected -> "Captive portal detected!\nURL: ${state.portalUrl ?: "Unknown"}"
                is CaptivePortalState.Checking -> "Checking..."
                is CaptivePortalState.Error -> "Error: ${state.message}"
            }
        }
    }

    private fun checkDnsHijack() {
        binding.statusText.text = "Checking for DNS hijacking..."

        lifecycleScope.launch {
            val result = dnsHijackDetector.detect()
            binding.statusText.text = when (result) {
                is DnsHijackResult.Clear -> "No DNS hijacking detected"
                is DnsHijackResult.Detected -> "DNS hijacking detected!\n${result.domain}: expected ${result.expectedIp}, got ${result.actualIp}"
                is DnsHijackResult.Error -> "Error: ${result.message}"
            }
        }
    }

    private fun makeTestRequest() {
        binding.statusText.text = "Making HTTP request..."

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/get")
                        .build()
                    httpClient.newCall(request).execute()
                }
                binding.statusText.text = "HTTP ${response.code}\nResponse logged to traffic viewer"
                response.close()
            } catch (e: Exception) {
                binding.statusText.text = "Request failed: ${e.message}"
            }
        }
    }

    private fun exportHar() {
        binding.statusText.text = "Exporting HAR..."

        lifecycleScope.launch {
            try {
                harShareHelper.shareAll()
                binding.statusText.text = "HAR export shared"
            } catch (e: Exception) {
                binding.statusText.text = "HAR export failed: ${e.message}"
            }
        }
    }

    private fun checkWifiInfo() {
        if (!hasLocationPermission()) {
            binding.statusText.text = "Location permission required for WiFi info"
            requestPermissions()
            return
        }

        binding.statusText.text = "Checking WiFi info..."

        lifecycleScope.launch {
            try {
                val info = wifiMonitor.getCurrentWifiInfo()
                binding.statusText.text = if (info != null) {
                    """
                    WiFi Info:
                    SSID: ${info.ssid ?: "Unknown"}
                    Signal: ${info.signalStrengthPercent}% (${info.signalQuality})
                    Speed: ${info.linkSpeedMbps} Mbps
                    Frequency: ${info.frequencyMhz} MHz (${info.band})
                    """.trimIndent()
                } else {
                    "Not connected to WiFi"
                }
            } catch (e: SecurityException) {
                binding.statusText.text = "Location permission required"
            }
        }
    }

    private fun measureLatency() {
        binding.statusText.text = "Measuring latency..."

        lifecycleScope.launch {
            val result = wifiMonitor.measureLatency("8.8.8.8")
            binding.statusText.text = """
                Latency to 8.8.8.8:
                Min: ${result.minMs}ms
                Max: ${result.maxMs}ms
                Avg: ${result.avgMs}ms
                Packet Loss: ${(result.packetLoss * 100).toInt()}%
            """.trimIndent()
        }
    }

    private fun checkConnectionQuality() {
        binding.statusText.text = "Measuring connection quality..."

        lifecycleScope.launch {
            try {
                val snapshot = qualityMonitor.measureQuality()
                binding.statusText.text = """
                    Connection Quality:
                    Latency: ${snapshot.latencyMs}ms
                    Jitter: ${snapshot.jitter.jitterMs}ms
                    Bandwidth: ${snapshot.bandwidth.downloadKbps} Kbps down
                    Signal: ${snapshot.signalStrengthDbm?.let { "${it} dBm" } ?: "N/A"}
                    Stability: ${(snapshot.stabilityScore * 100).toInt()}%
                    Quality: ${snapshot.overallQuality}
                """.trimIndent()
            } catch (e: Exception) {
                binding.statusText.text = "Quality measurement failed: ${e.message}"
            }
        }
    }

    private fun checkVpn() {
        binding.statusText.text = "Checking VPN state..."

        lifecycleScope.launch {
            try {
                val state = vpnDetector.detect()
                val dnsLeak = vpnDetector.checkDnsLeak()
                val splitTunnel = vpnDetector.detectSplitTunneling()

                val vpnInfo = when (state) {
                    is VpnState.Connected -> """
                        VPN Active:
                        Interface: ${state.interfaceName}
                        Protocol: ${state.protocolHint}
                        Addresses: ${state.addresses.joinToString(", ")}
                    """.trimIndent()
                    is VpnState.Disconnected -> "VPN: Not connected"
                    is VpnState.Error -> "VPN Error: ${state.message}"
                }

                val dnsInfo = when (dnsLeak) {
                    is DnsLeakResult.NoLeak -> "\nDNS Leak: None detected"
                    is DnsLeakResult.LeakDetected -> "\nDNS Leak: DETECTED\nLeaked: ${dnsLeak.leakedServers.joinToString(", ")}"
                    is DnsLeakResult.NoVpn -> "\nDNS Leak: N/A (no VPN)"
                    is DnsLeakResult.Error -> "\nDNS Leak: Error - ${dnsLeak.message}"
                }

                val splitInfo = when (splitTunnel) {
                    is SplitTunnelingResult.FullTunnel -> "\nSplit Tunnel: No (full tunnel)"
                    is SplitTunnelingResult.SplitTunnel -> "\nSplit Tunnel: Yes (${splitTunnel.vpnRoutes.size} VPN routes)"
                    is SplitTunnelingResult.NoVpn -> "\nSplit Tunnel: N/A (no VPN)"
                    is SplitTunnelingResult.Error -> "\nSplit Tunnel: Error - ${splitTunnel.message}"
                }

                binding.statusText.text = vpnInfo + dnsInfo + splitInfo
            } catch (e: Exception) {
                binding.statusText.text = "VPN check failed: ${e.message}"
            }
        }
    }

    private fun securityScan() {
        binding.statusText.text = "Running security scan on google.com..."

        lifecycleScope.launch {
            try {
                val report = securityAnalyzer.analyze("google.com")
                val ctStatus = when (report.ctResult) {
                    is CertificateTransparencyResult.Valid -> "Valid (${(report.ctResult as CertificateTransparencyResult.Valid).sctCount} SCTs)"
                    is CertificateTransparencyResult.Invalid -> "No SCTs found"
                    is CertificateTransparencyResult.Error -> "Error"
                }

                binding.statusText.text = """
                    Security Report: google.com
                    TLS: ${report.tlsResult.tlsVersion}
                    Cipher: ${report.tlsResult.cipherSuite}
                    Weak Cipher: ${report.tlsResult.isWeakCipher}
                    Deprecated: ${report.tlsResult.isDeprecatedProtocol}
                    CT: $ctStatus
                    Cert Chain: ${report.tlsResult.certificateChain.size} certs
                    Risk: ${report.overallRisk}
                    Warnings: ${report.warnings.size}
                """.trimIndent()

                if (report.warnings.isNotEmpty()) {
                    val warningText = report.warnings.joinToString("\n") {
                        "  [${it.severity}] ${it.message}"
                    }
                    binding.statusText.append("\n\n$warningText")
                }
            } catch (e: Exception) {
                binding.statusText.text = "Security scan failed: ${e.message}"
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}
