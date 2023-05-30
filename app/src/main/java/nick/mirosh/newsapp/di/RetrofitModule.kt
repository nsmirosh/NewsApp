package nick.mirosh.newsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.networking.HeaderInterceptor
import nick.mirosh.newsapp.networking.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class RetrofitModule {

    @Provides
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

    @Provides
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