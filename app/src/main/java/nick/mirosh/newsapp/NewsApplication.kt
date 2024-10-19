package nick.mirosh.newsapp

import android.app.Application
import nick.mirosh.newsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    // Need to initialize NetworkConnectivityManager straight away so that there is a freshest
    // status of the network connection even on the first screen
//    @Inject
//    lateinit var networkConnectivityManager: NetworkConnectivityService

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(appModule)
        }
    }
}