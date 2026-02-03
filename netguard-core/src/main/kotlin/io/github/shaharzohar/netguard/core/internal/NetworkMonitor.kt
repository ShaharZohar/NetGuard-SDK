package io.github.shaharzohar.netguard.core.internal

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.github.shaharzohar.netguard.core.models.NetworkState
import io.github.shaharzohar.netguard.core.models.NetworkType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

/**
 * Interface for monitoring network state changes.
 */
internal interface NetworkMonitor {
    /**
     * Observe network state changes as a [Flow].
     */
    fun observeNetworkState(): Flow<NetworkState>

    /**
     * Get the current network state synchronously.
     */
    fun getCurrentNetworkState(): NetworkState
}

/**
 * Implementation of [NetworkMonitor] using Android's ConnectivityManager.
 */
internal class NetworkMonitorImpl(
    private val connectivityManager: ConnectivityManager,
    private val dispatcher: CoroutineDispatcher
) : NetworkMonitor {

    override fun observeNetworkState(): Flow<NetworkState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(getNetworkStateFromCapabilities(network))
            }

            override fun onLost(network: Network) {
                trySend(NetworkState.Disconnected)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                trySend(parseCapabilities(capabilities))
            }

            override fun onUnavailable() {
                trySend(NetworkState.Disconnected)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Emit initial state
        trySend(getCurrentNetworkState())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.flowOn(dispatcher)

    override fun getCurrentNetworkState(): NetworkState {
        val activeNetwork = connectivityManager.activeNetwork
            ?: return NetworkState.Disconnected
        return getNetworkStateFromCapabilities(activeNetwork)
    }

    private fun getNetworkStateFromCapabilities(network: Network): NetworkState {
        val capabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return NetworkState.Disconnected
        return parseCapabilities(capabilities)
    }

    private fun parseCapabilities(capabilities: NetworkCapabilities): NetworkState {
        val type = when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> NetworkType.VPN
            else -> NetworkType.UNKNOWN
        }

        val hasCaptivePortal = capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_CAPTIVE_PORTAL
        )

        val isValidated = capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )

        val hasInternet = capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )

        return NetworkState.Connected(
            type = type,
            hasCaptivePortal = hasCaptivePortal,
            isValidated = isValidated,
            hasInternet = hasInternet,
            linkDownstreamBandwidthKbps = capabilities.linkDownstreamBandwidthKbps,
            linkUpstreamBandwidthKbps = capabilities.linkUpstreamBandwidthKbps
        )
    }
}
