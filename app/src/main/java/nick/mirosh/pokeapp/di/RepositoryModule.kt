package nick.mirosh.pokeapp.di

import android.util.Log
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
    @Universal
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        appDatabase: ArticleDao
    ): NewsRepository {
        val repo =  NewsRepositoryImpl(
            newsRemoteDataSource,
            appDatabase
        )
        Log.d("RepositoryModule", "@Universal NewsRepository.hashCode = ${repo.hashCode()}")
        return repo
    }

    @Cache
    @Provides
    fun provideCacheNewsRepository(
        appDatabase: ArticleDao
    ): NewsRepository {

        val repo =  NewsRepositoryImpl(
            dao = appDatabase
        )
        Log.d("RepositoryModule", "@Cache NewsRepository.hashCode = ${repo.hashCode()}")
        return repo
    }
}