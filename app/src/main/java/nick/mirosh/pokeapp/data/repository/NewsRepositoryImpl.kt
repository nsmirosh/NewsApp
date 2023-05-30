package nick.mirosh.pokeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import nick.mirosh.pokeapp.database.ArticleDao
import nick.mirosh.pokeapp.entity.Article
import nick.mirosh.pokeapp.entity.asDatabaseArticle
import nick.mirosh.pokeapp.entity.asDatabaseModel
import nick.mirosh.pokeapp.entity.asDomainModel
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsRemoteDataSource? = null,
    private val dao: ArticleDao
) : NewsRepository {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    override val articles: StateFlow<List<Article>> = _articles

    override suspend fun getNewsList() {
        withContext(Dispatchers.IO) {
            _articles.value = try {
                val networkArticles = newsDataSource?.getHeadlines() ?: emptyList()
                if (networkArticles.isNotEmpty()) {
                    dao.insertAll(networkArticles.map {
                        it.asDatabaseArticle()
                    })
                }
                networkArticles.map {
                    it.asDomainModel()
                }
            } catch (e: Exception) {
                dao.getAllArticles().map {
                    it.asDomainModel()
                }
            }
        }
    }

    override suspend fun getFavoriteArticles() {
        withContext(Dispatchers.IO) {
            val likedArticles = dao.getLikedArticles().map {
                it.asDomainModel()
            }
            _articles.value = likedArticles
        }
    }

    override suspend fun saveLikedArticle(article: Article) {
        withContext(Dispatchers.IO) {
            dao.insert(article.asDatabaseModel())
        }
    }
}