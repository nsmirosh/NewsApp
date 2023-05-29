package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.DatabaseArticle
import nick.mirosh.pokeapp.entity.NetworkArticle
import nick.mirosh.pokeapp.networking.NewsService
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) {
    suspend fun getHeadlines(): List<NetworkArticle>? {
        val response = newsService.getHeadlines("us").execute().body()
        return response?.articles
    }
}