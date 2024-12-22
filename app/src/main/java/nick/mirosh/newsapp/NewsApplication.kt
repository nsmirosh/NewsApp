package nick.mirosh.newsapp

import android.app.Application
import nick.mirosh.newsapp.di.appModule
import nick.mirosh.newsapp.di.dispatcherModule
import nick.mirosh.newsapp.di.networkModule
import nick.mirosh.newsapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(appModule, networkModule, dispatcherModule, repositoryModule)

        }
    }
}