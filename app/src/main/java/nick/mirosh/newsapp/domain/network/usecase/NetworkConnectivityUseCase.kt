package nick.mirosh.newsapp.domain.network.usecase

import kotlinx.coroutines.flow.Flow
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Inject

class NetworkConnectivityUseCase @Inject constructor(
    private val repository: NetworkConnectivityService
) {
    operator fun invoke(): Flow<Boolean> = repository.isAvailable()
}
