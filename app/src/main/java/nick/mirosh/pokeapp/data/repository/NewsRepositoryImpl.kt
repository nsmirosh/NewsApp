package nick.mirosh.pokeapp.data.repository

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NewsRepositoryImpl(private val newsDataSource: NewsDataSource) : NewsRepository {

    override suspend fun getNewsList(coroutineScope: CoroutineContext) =
        withContext(coroutineScope) {
            newsDataSource.getHeadlines() ?: emptyList()
        }
}