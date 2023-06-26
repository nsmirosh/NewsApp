package nick.mirosh.newsapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.database.ArticleDao
import nick.mirosh.newsapp.entity.Article
import nick.mirosh.newsapp.entity.asDatabaseArticle
import nick.mirosh.newsapp.entity.asDatabaseModel
import nick.mirosh.newsapp.entity.asDomainModel
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsRemoteDataSource? = null,
    private val articleDao: ArticleDao
) : NewsRepository {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    override val articles: StateFlow<List<Article>> = _articles

    override suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            try {
                val networkArticles = newsDataSource?.getHeadlines() ?: emptyList()
                if (networkArticles.isNotEmpty()) {
                    val result = articleDao.insertAll(networkArticles.map {
                        it.asDatabaseArticle()
                    })
                    Log.d("NewsRepositoryImpl", "refreshNews: result = $result")
                }
            } finally {
                getAllArticlesFromDb()
            }
        }
    }

    override suspend fun getFavoriteArticles() {
        withContext(Dispatchers.IO) {
            val likedArticles = articleDao.getLikedArticles().map {
                it.asDomainModel()
            }
            _articles.value = likedArticles
        }
    }

    override suspend fun updateArticle(article: Article) {
        withContext(Dispatchers.IO) {
            articleDao.insert(article.asDatabaseModel())
            getAllArticlesFromDb()
        }
    }

    private fun getAllArticlesFromDb() {
        _articles.value = articleDao.getAllArticles()
            .map { it.asDomainModel() }
            .sortedBy { it.url }
    }
}