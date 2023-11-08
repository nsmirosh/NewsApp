package nick.mirosh.newsapp.domain.network.repository

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityService {
    fun isAvailable(): Flow<Boolean>
}
