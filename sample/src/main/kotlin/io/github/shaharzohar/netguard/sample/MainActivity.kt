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
import io.github.shaharzohar.netguard.okhttp.addNetGuardInterceptor
import io.github.shaharzohar.netguard.sample.databinding.ActivityMainBinding
import io.github.shaharzohar.netguard.traffic.ui.TrafficLogActivity
import io.github.shaharzohar.netguard.wifi.monitor.WifiMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
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

            // Check WiFi Info
            checkWifiButton.setOnClickListener {
                checkWifiInfo()
            }

            // Measure Latency
            measureLatencyButton.setOnClickListener {
                measureLatency()
            }
        }
    }

    private fun checkCaptivePortal() {
        binding.statusText.text = "Checking for captive portal..."
        
        lifecycleScope.launch {
            val state = captivePortalDetector.checkNow()
            binding.statusText.text = when (state) {
                is CaptivePortalState.Clear -> "✅ No captive portal detected"
                is CaptivePortalState.Detected -> "⚠️ Captive portal detected!\nURL: ${state.portalUrl ?: "Unknown"}"
                is CaptivePortalState.Checking -> "🔄 Checking..."
                is CaptivePortalState.Error -> "❌ Error: ${state.message}"
            }
        }
    }

    private fun checkDnsHijack() {
        binding.statusText.text = "Checking for DNS hijacking..."
        
        lifecycleScope.launch {
            val result = dnsHijackDetector.detect()
            binding.statusText.text = when (result) {
                is DnsHijackResult.Clear -> "✅ No DNS hijacking detected"
                is DnsHijackResult.Detected -> "⚠️ DNS hijacking detected!\n${result.domain}: expected ${result.expectedIp}, got ${result.actualIp}"
                is DnsHijackResult.Error -> "❌ Error: ${result.message}"
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
                binding.statusText.text = "✅ HTTP ${response.code}\nResponse logged to traffic viewer"
                response.close()
            } catch (e: Exception) {
                binding.statusText.text = "❌ Request failed: ${e.message}"
            }
        }
    }

    private fun checkWifiInfo() {
        if (!hasLocationPermission()) {
            binding.statusText.text = "⚠️ Location permission required for WiFi info"
            requestPermissions()
            return
        }

        binding.statusText.text = "Checking WiFi info..."
        
        lifecycleScope.launch {
            try {
                val info = wifiMonitor.getCurrentWifiInfo()
                binding.statusText.text = if (info != null) {
                    """
                    📶 WiFi Info:
                    SSID: ${info.ssid ?: "Unknown"}
                    Signal: ${info.signalStrengthPercent}% (${info.signalQuality})
                    Speed: ${info.linkSpeedMbps} Mbps
                    Frequency: ${info.frequencyMhz} MHz (${info.band})
                    """.trimIndent()
                } else {
                    "⚠️ Not connected to WiFi"
                }
            } catch (e: SecurityException) {
                binding.statusText.text = "⚠️ Location permission required"
            }
        }
    }

    private fun measureLatency() {
        binding.statusText.text = "Measuring latency..."
        
        lifecycleScope.launch {
            val result = wifiMonitor.measureLatency("8.8.8.8")
            binding.statusText.text = """
                📊 Latency to 8.8.8.8:
                Min: ${result.minMs}ms
                Max: ${result.maxMs}ms
                Avg: ${result.avgMs}ms
                Packet Loss: ${(result.packetLoss * 100).toInt()}%
            """.trimIndent()
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
