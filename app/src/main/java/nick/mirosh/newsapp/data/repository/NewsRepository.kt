package nick.mirosh.newsapp.data.repository

import kotlinx.coroutines.flow.StateFlow
import nick.mirosh.newsapp.entity.Article

interface NewsRepository {

    val articles: StateFlow<List<Article>>
    suspend fun refreshNews()

    suspend fun getFavoriteArticles()

    suspend fun updateArticle(article: Article)
}