package io.github.shaharzohar.netguard.okhttp

import android.content.Context
import io.github.shaharzohar.netguard.core.NetGuard
import io.github.shaharzohar.netguard.traffic.interceptor.NetGuardInterceptor
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

/**
 * Extension functions for easily integrating NetGuard with OkHttp.
 *
 * @since 1.0.0
 */

/**
 * Add NetGuard traffic logging interceptor to OkHttpClient.
 *
 * ```kotlin
 * val client = OkHttpClient.Builder()
 *     .addNetGuardInterceptor()
 *     .build()
 * ```
 */
fun OkHttpClient.Builder.addNetGuardInterceptor(): OkHttpClient.Builder {
    return addInterceptor(NetGuardInterceptor())
}

/**
 * Add NetGuard as a network interceptor (sees raw network traffic).
 */
fun OkHttpClient.Builder.addNetGuardNetworkInterceptor(): OkHttpClient.Builder {
    return addNetworkInterceptor(NetGuardInterceptor())
}

/**
 * Configure DNS-over-HTTPS using Cloudflare.
 */
fun OkHttpClient.Builder.withCloudflareDoH(bootstrapClient: OkHttpClient): OkHttpClient.Builder {
    val dns = DnsOverHttps.Builder()
        .client(bootstrapClient)
        .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
        .bootstrapDnsHosts(
            InetAddress.getByName("1.1.1.1"),
            InetAddress.getByName("1.0.0.1")
        )
        .build()
    return dns(dns)
}

/**
 * Configure DNS-over-HTTPS using Google.
 */
fun OkHttpClient.Builder.withGoogleDoH(bootstrapClient: OkHttpClient): OkHttpClient.Builder {
    val dns = DnsOverHttps.Builder()
        .client(bootstrapClient)
        .url("https://dns.google/dns-query".toHttpUrl())
        .bootstrapDnsHosts(
            InetAddress.getByName("8.8.8.8"),
            InetAddress.getByName("8.8.4.4")
        )
        .build()
    return dns(dns)
}

private fun String.toHttpUrl() = okhttp3.HttpUrl.Builder()
    .scheme("https")
    .host(this.removePrefix("https://").substringBefore("/"))
    .encodedPath("/" + this.substringAfter("/", "dns-query"))
    .build()

/**
 * Create a pre-configured OkHttpClient with NetGuard integration.
 *
 * ```kotlin
 * val client = NetGuardOkHttpClient.create(context)
 * ```
 */
object NetGuardOkHttpClient {
    
    /**
     * Create an OkHttpClient with NetGuard interceptor.
     */
    fun create(context: Context = NetGuard.getContext()): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetGuardInterceptor()
            .build()
    }

    /**
     * Create an OkHttpClient with NetGuard and DNS-over-HTTPS.
     */
    fun createWithDoH(
        context: Context = NetGuard.getContext(),
        provider: DoHProvider = DoHProvider.CLOUDFLARE
    ): OkHttpClient {
        val bootstrapClient = OkHttpClient()
        
        return OkHttpClient.Builder()
            .addNetGuardInterceptor()
            .apply {
                when (provider) {
                    DoHProvider.CLOUDFLARE -> withCloudflareDoH(bootstrapClient)
                    DoHProvider.GOOGLE -> withGoogleDoH(bootstrapClient)
                }
            }
            .build()
    }
}

enum class DoHProvider {
    CLOUDFLARE,
    GOOGLE
}
