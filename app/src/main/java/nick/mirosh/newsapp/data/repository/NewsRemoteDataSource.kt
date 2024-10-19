package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.data.models.ArticleDTO
import nick.mirosh.newsapp.data.networking.NewsService

class NewsRemoteDataSource (private val newsService: NewsService) {
    suspend fun getHeadlines(): List<ArticleDTO> {
        val response = newsService.getHeadlines("us")
        return response.articles
    }
}