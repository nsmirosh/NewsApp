package nick.mirosh.pokeapp.data.repository

import kotlinx.coroutines.flow.StateFlow
import nick.mirosh.pokeapp.entity.Article

interface NewsRepository {

    val articles: StateFlow<List<Article>>
    suspend fun getNewsList()

    suspend fun getFavoriteArticles()

    suspend fun saveLikedArticle(article: Article)
}