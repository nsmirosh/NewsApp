package nick.mirosh.newsapp.domain.usecase.articles

import kotlinx.coroutines.flow.Flow
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository

class FetchFavoriteArticlesUsecase (
    private val repository: NewsRepository,
) {

    suspend operator fun invoke(): Flow<Result<List<Article>>> = repository.getFavoriteArticles()

}