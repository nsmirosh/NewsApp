package nick.mirosh.newsapp.domain.usecase.articles

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository

class FetchFavoriteArticlesUsecase (
    private val repository: NewsRepository,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(): Result<List<Article>> {
        return withContext(coroutineDispatcher) {
            repository.getFavoriteArticles()
        }
    }
}