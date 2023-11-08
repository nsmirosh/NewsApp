package nick.mirosh.newsapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Universal
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        appDatabase: ArticleDao,
        databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
        dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
    ): NewsRepository =
        NewsRepositoryImpl(
            newsRemoteDataSource,
            appDatabase,
            dtoToDatabaseArticleMapper = dtoToDatabaseArticleMapper,
            databaseToDomainArticleMapper = databaseToDomainArticleMapper,
        )

    @Cache
    @Provides
    fun provideCacheNewsRepository(
        appDatabase: ArticleDao,
        databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
        dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
    ): NewsRepository =
        NewsRepositoryImpl(
            newsLocalDataSource = appDatabase,
            dtoToDatabaseArticleMapper = dtoToDatabaseArticleMapper,
            databaseToDomainArticleMapper = databaseToDomainArticleMapper,
        )
}