package nick.mirosh.pokeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.pokeapp.data.repository.NewsRemoteDataSource
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.data.repository.NewsRepositoryImpl
import nick.mirosh.pokeapp.database.ArticleDao

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        appDatabase: ArticleDao
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsRemoteDataSource,
            appDatabase
        )
    }
}