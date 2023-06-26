package nick.mirosh.newsapp.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.database.ArticleDao

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Universal
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        articleDao: ArticleDao
    ): NewsRepository {
        val newsRepositoryImpl = NewsRepositoryImpl(newsRemoteDataSource, articleDao)
        Log.d(
            "RepositoryModule",
            "@Universal newsRepositoryImpl.hashCode = ${newsRepositoryImpl.hashCode()}"
        )
        return newsRepositoryImpl
    }

    @Cache
    @Provides
    fun provideСacheNewsRepository(
        articleDao: ArticleDao
    ): NewsRepository {
        val newsRepositoryImpl = NewsRepositoryImpl(articleDao = articleDao)
        Log.d(
            "RepositoryModule",
            "@Cache newsRepositoryImpl.hashCode = ${newsRepositoryImpl.hashCode()}"
        )
        return newsRepositoryImpl
    }
}