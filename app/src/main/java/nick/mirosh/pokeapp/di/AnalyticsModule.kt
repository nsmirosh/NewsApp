package nick.mirosh.pokeapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.pokeapp.data.repository.NewsDataSource
import nick.mirosh.pokeapp.data.repository.NewsRemoteDataSource
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.data.repository.NewsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class AnalyticsModule {

  @Binds
  abstract fun bindRepository(
    newsRepositoryImpl: NewsRepositoryImpl
  ): NewsRepository

  @Binds
  abstract fun bindDataSource(
    newsRemoteDataSource: NewsRemoteDataSource
  ): NewsDataSource


}