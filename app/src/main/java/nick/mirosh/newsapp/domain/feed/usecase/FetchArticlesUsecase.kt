package nick.mirosh.newsapp.domain.feed.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository

class FetchArticlesUsecase(
    private val repository: NewsRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(country: String): Result<List<Article>> {
        return withContext(coroutineDispatcher) {
            repository.getNewsArticles(country)
        }
    }
}