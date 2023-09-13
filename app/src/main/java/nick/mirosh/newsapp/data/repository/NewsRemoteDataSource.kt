package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.entity.NetworkArticle
import nick.mirosh.newsapp.networking.NewsService

class NewsRemoteDataSource constructor(private val newsService: NewsService) {
    suspend fun getHeadlines(): List<NetworkArticle>? {
        val response = newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}