package nick.mirosh.newsapp.domain.usecase.articles

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.di.Cache
import nick.mirosh.newsapp.di.IoDispatcher
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.models.Article
import javax.inject.Inject

class LikeArticleUsecase @Inject constructor(
    @Cache private val repository: NewsRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(article: Article): DomainState<Article> {
        return withContext(coroutineDispatcher) {
            repository.updateArticle(article)
        }
    }
}