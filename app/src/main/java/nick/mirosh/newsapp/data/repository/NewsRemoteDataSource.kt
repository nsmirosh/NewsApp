package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.entity.NetworkArticle
import nick.mirosh.newsapp.networking.NewsService
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) {
    suspend fun getHeadlines(): List<NetworkArticle>? {
        val response = newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}