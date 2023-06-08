package nick.mirosh.newsapp

import android.app.Application
import nick.mirosh.newsapp.di.AppContainer

class NewsApplication : Application() {

    val appContainer = AppContainer(this)
}