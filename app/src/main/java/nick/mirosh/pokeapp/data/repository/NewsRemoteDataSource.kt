package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.ArticleDTO
import nick.mirosh.pokeapp.networking.NewsService
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) :
    NewsDataSource {
    override suspend fun getHeadlines(): List<ArticleDTO>? {
        val response = newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}