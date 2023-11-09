package nick.mirosh.newsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TestNetworkBehaviorModule {

    @Provides
    fun provideNetworkResponseBehavior(): NetworkResponseBehavior =
        NetworkResponseBehavior.NETWORK_AVAILABLE
}
