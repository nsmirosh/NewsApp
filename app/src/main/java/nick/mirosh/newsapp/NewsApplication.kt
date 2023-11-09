package nick.mirosh.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() {
    // Need to initialize NetworkConnectivityManager straight away so that there is a freshest
    // status of the network connection even on the first screen
    @Inject
    lateinit var networkConnectivityManager: NetworkConnectivityService
}