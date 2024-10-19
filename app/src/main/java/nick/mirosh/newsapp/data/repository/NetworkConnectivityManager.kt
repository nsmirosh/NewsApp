package nick.mirosh.newsapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService

class NetworkConnectivityManager (
    context: Context,
) : NetworkConnectivityService {

    private val _isNetworkAvailable = MutableStateFlow(false)

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private val connectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
                _isNetworkAvailable.value = true
        }


        override fun onLost(network: Network) {
            super.onLost(network)
            _isNetworkAvailable.value = false
        }
    }

    init {
        connectivityManager?.requestNetwork(networkRequest, networkCallback)
    }

    override fun isAvailable() = _isNetworkAvailable

    fun stop() {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}
