package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.ArticleDTO
import nick.mirosh.pokeapp.networking.RetrofitClient
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor() : NewsDataSource {
    override suspend fun getHeadlines(): List<ArticleDTO>? {
        val api = RetrofitClient.instance!!.newsApi
        val response = api.getHeadlines("ua").execute().body()
        return response?.articles
    }
}