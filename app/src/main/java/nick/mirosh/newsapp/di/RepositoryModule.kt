package nick.mirosh.newsapp.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
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
        @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher,
        newsRemoteDataSource: NewsRemoteDataSource,
        appDatabase: ArticleDao
    ): NewsRepository {
        val repo = NewsRepositoryImpl(
            coroutineDispatcher,
            newsRemoteDataSource,
            appDatabase
        )
        Log.d("RepositoryModule", "@Universal NewsRepository.hashCode = ${repo.hashCode()}")
        return repo
    }

    @Cache
    @Provides
    fun provideCacheNewsRepository(
        @DefaultDispatcher coroutineDispatcher: CoroutineDispatcher,
        appDatabase: ArticleDao
    ): NewsRepository {
        val repo = NewsRepositoryImpl(
            coroutineDispatcher = coroutineDispatcher,
            newsLocalDataSource = appDatabase
        )
        Log.d("RepositoryModule", "@Cache NewsRepository.hashCode = ${repo.hashCode()}")
        return repo
    }
}