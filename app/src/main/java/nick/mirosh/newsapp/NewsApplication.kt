package nick.mirosh.newsapp

import android.app.Application
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.networking.NewsService
import nick.mirosh.newsapp.ui.MainViewModel
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

class NewsApplication : Application() {

    private val mainAppModule = module {

//        factory { NewsRemoteDataSource(get()) }


        factoryOf(::NewsRemoteDataSource)
        single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
        factoryOf(::NewsRepositoryImpl)
//        factory { NewsRepositoryImpl(get(), get()) }
        viewModel { MainViewModel(get()) }
    }

    private val favoriteArticlesModule = module {
        viewModel { FavoriteArticlesViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@NewsApplication)
            // Load modules
            modules(mainAppModule, favoriteArticlesModule)
        }
    }

}