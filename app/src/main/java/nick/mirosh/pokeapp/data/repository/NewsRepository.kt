package nick.mirosh.pokeapp.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import nick.mirosh.pokeapp.entity.Article
import nick.mirosh.pokeapp.entity.DatabaseArticle

interface NewsRepository {

    var articles: Flow<List<Article>>
    suspend fun getNewsList()

    suspend fun getFavoriteArticles()

    suspend fun saveLikedArticle(article: Article)
}