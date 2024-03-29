package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.data.models.ArticleDTO
import nick.mirosh.newsapp.data.networking.NewsService
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) {
    suspend fun getHeadlines(): List<ArticleDTO> {
        val response = newsService.getHeadlines("us")
        return response.articles
    }
}