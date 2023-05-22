package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.ArticleDTO
import kotlin.coroutines.CoroutineContext

interface NewsRepository {

    suspend fun getNewsList(coroutineContext: CoroutineContext): List<ArticleDTO>
}