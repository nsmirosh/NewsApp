package nick.mirosh.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    @ExperimentalCoroutinesApi
    @Singleton
    abstract fun bindNetworkConnectivityRepository(
        networkConnectivityManager: NetworkConnectivityManager
    ): NetworkConnectivityService
}