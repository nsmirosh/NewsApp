package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.ArticleDTO
import nick.mirosh.pokeapp.networking.RetrofitClient

class NewsRemoteDataSource : NewsDataSource {
    override suspend fun getHeadlines(): List<ArticleDTO>? {
        val api = RetrofitClient.instance!!.newsApi
        val response = api.getHeadlines("ua").execute().body()
        return response?.articles
    }
}