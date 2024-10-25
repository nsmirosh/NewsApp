package nick.mirosh.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Singleton
//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [ServiceModule::class]
//)
//abstract class TestServiceModule {
//
//    @Binds
//    @ExperimentalCoroutinesApi
//    @Singleton
//    abstract fun bindNetworkConnectivityRepository(
//        networkConnectivityManager: TestNetworkConnectivityService
//    ): NetworkConnectivityService
//}