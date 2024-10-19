package nick.mirosh.newsapp.di

import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper

class RepositoryModule {

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
}