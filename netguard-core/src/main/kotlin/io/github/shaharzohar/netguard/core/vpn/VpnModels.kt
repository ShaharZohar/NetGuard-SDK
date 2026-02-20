package io.github.shaharzohar.netguard.core.vpn

/**
 * Represents the current VPN connection state.
 *
 * @since 1.1.0
 */
sealed class VpnState {

    /**
     * No VPN connection is active.
     */
    data object Disconnected : VpnState()

    /**
     * A VPN connection is active.
     *
     * @property interfaceName Name of the VPN network interface (e.g., "tun0", "wg0")
     * @property protocolHint Detected VPN protocol based on interface naming conventions
     * @property addresses List of IP addresses assigned to the VPN interface
     */
    data class Connected(
        val interfaceName: String,
        val protocolHint: VpnProtocolHint,
        val addresses: List<String>
    ) : VpnState()

    /**
     * An error occurred while detecting VPN state.
     */
    data class Error(val message: String, val cause: Throwable? = null) : VpnState()

    val isActive: Boolean
        get() = this is Connected
}

/**
 * Heuristic VPN protocol detection based on network interface name.
 *
 * @since 1.1.0
 */
enum class VpnProtocolHint {
    /** OpenVPN (tun interface) */
    OPENVPN,
    /** WireGuard (wg interface) */
    WIREGUARD,
    /** IPSec/IKEv2 (ipsec interface) */
    IPSEC,
    /** PPTP (ppp interface) */
    PPTP,
    /** Protocol could not be determined */
    UNKNOWN
}

/**
 * Result of a DNS leak test.
 *
 * @since 1.1.0
 */
sealed class DnsLeakResult {

    /**
     * No DNS leak detected — all DNS servers are within the VPN tunnel.
     */
    data class NoLeak(
        val vpnDnsServers: List<String>
    ) : DnsLeakResult()

    /**
     * Potential DNS leak detected — some DNS servers are outside the VPN tunnel.
     *
     * @property leakedServers DNS servers that are not routed through the VPN
     * @property vpnDnsServers DNS servers that are routed through the VPN
     */
    data class LeakDetected(
        val leakedServers: List<String>,
        val vpnDnsServers: List<String>
    ) : DnsLeakResult()

    /**
     * No VPN is active, so DNS leak testing is not applicable.
     */
    data object NoVpn : DnsLeakResult()

    /**
     * An error occurred during the DNS leak test.
     */
    data class Error(val message: String, val cause: Throwable? = null) : DnsLeakResult()
}

/**
 * Result of split tunneling detection.
 *
 * @since 1.1.0
 */
sealed class SplitTunnelingResult {

    /**
     * Full tunnel — all traffic is routed through the VPN.
     */
    data object FullTunnel : SplitTunnelingResult()

    /**
     * Split tunnel detected — only some traffic goes through the VPN.
     *
     * @property vpnRoutes Routes handled by the VPN
     * @property directRoutes Routes that bypass the VPN
     */
    data class SplitTunnel(
        val vpnRoutes: List<String>,
        val directRoutes: List<String>
    ) : SplitTunnelingResult()

    /**
     * No VPN is active.
     */
    data object NoVpn : SplitTunnelingResult()

    /**
     * An error occurred during detection.
     */
    data class Error(val message: String, val cause: Throwable? = null) : SplitTunnelingResult()
}
