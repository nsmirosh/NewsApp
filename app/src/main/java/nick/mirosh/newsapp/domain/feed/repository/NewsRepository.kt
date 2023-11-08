package nick.mirosh.newsapp.domain.feed.repository

import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.feed.model.Article

interface NewsRepository {
    suspend fun getNewsArticles(): Resource<List<Article>>
    suspend fun getFavoriteArticles(): Resource<List<Article>>
    suspend fun updateArticle(article: Article): Resource<Article>
}
