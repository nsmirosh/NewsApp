package nick.mirosh.newsapp.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService

abstract class ServiceModule {

    @ExperimentalCoroutinesApi
    abstract fun bindNetworkConnectivityRepository(
        networkConnectivityManager: NetworkConnectivityManager
    ): NetworkConnectivityService
}