package nick.mirosh.newsapp.di

import kotlinx.coroutines.flow.flow
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Inject

class TestNetworkConnectivityService @Inject constructor(
    private val behavior: NetworkResponseBehavior
) : NetworkConnectivityService {

    override fun isAvailable() = flow {
        emit(behavior == NetworkResponseBehavior.NETWORK_AVAILABLE)
    }
}

enum class NetworkResponseBehavior {
    NETWORK_AVAILABLE, NO_NETWORK
}
