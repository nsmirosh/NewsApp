package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.entity.NetworkArticle
import nick.mirosh.newsapp.networking.NewsAPI

class NewsRemoteDataSource(private val newsAPI: NewsAPI) {
    suspend fun getHeadlines(): List<NetworkArticle>? {
        val response = newsAPI.newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}