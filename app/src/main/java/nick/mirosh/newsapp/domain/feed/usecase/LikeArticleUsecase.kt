package nick.mirosh.newsapp.domain.feed.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.di.Cache
import nick.mirosh.newsapp.di.IoDispatcher
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import javax.inject.Inject

class LikeArticleUsecase @Inject constructor(
    @Cache private val repository: NewsRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(article: Article): Result<Article> {
        return withContext(coroutineDispatcher) {
            repository.updateArticle(article.copy(liked = !article.liked))
        }
    }
}