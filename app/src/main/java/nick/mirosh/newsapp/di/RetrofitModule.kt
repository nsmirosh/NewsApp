package nick.mirosh.newsapp.di

import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.data.networking.HeaderInterceptor
import nick.mirosh.newsapp.data.networking.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule {

    fun provideNewsService(
        okHttpClient: OkHttpClient
    ): NewsService {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(NewsService::class.java)
    }

    fun provideOkHttpWithLogger(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}