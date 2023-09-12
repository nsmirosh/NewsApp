package nick.mirosh.newsapp.data.repository

import android.util.Log
import nick.mirosh.newsapp.networking.NewsService
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) {
    fun getHeadlines(): List<NetworkArticle> {
        val response = newsService.getHeadlines("us", 20).execute().body()
        Log.d("NewsRemoteDataSource", "getHeadlines() response = ${response!!.articles.size}")
        return response.articles
    }
}