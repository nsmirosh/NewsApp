package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.entity.NetworkArticle
import nick.mirosh.newsapp.networking.HeaderInterceptor
import nick.mirosh.newsapp.networking.NewsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRemoteDataSource {

    private val newsService: NewsService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(HeaderInterceptor())
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        newsService = retrofit.create(NewsService::class.java)
    }

    suspend fun getHeadlines(): List<NetworkArticle>? {
        val response = newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}