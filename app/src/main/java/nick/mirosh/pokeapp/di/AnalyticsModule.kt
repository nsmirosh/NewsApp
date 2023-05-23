package nick.mirosh.pokeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nick.mirosh.pokeapp.BuildConfig
import nick.mirosh.pokeapp.networking.HeaderInterceptor
import nick.mirosh.pokeapp.networking.NewsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AnalyticsModule {

  @Provides
  fun provideNewsService(
    // Potential dependencies of this type
  ): NewsService {
      val okHttpClient = OkHttpClient.Builder()
          .addInterceptor(HeaderInterceptor())
          .build()

      val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build()

       return retrofit.create(NewsService::class.java)
  }
}