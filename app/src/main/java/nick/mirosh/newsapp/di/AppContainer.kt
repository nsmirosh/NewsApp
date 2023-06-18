package nick.mirosh.newsapp.di

import android.app.Application
import androidx.room.Room
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.database.AppDatabase
import nick.mirosh.newsapp.database.ArticleDao
import nick.mirosh.newsapp.database.DATABASE_NAME
import nick.mirosh.newsapp.networking.NewsAPI

class AppContainer(application: Application) {

    private val dao: ArticleDao = Room
        .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
        .build()
        .articleDao()

    private val newsAPI = NewsAPI()

    private val newsRemoteDataSource = NewsRemoteDataSource(newsAPI)

    private val newsRepository = NewsRepositoryImpl(newsRemoteDataSource, dao)

    val favoriteArticlesViewModelFactory = FavoriteArticlesViewModelFactory(newsRepository)

    val mainViewModelFactory = MainViewModelFactory(newsRepository)

}