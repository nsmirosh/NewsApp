package nick.mirosh.newsapp.di

import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.data.networking.HeaderInterceptor
import nick.mirosh.newsapp.data.networking.NewsService
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<NewsService> { get<Retrofit>().create(NewsService::class.java) }

    singleOf(::NetworkConnectivityManager) { bind<NetworkConnectivityService>() }
}
