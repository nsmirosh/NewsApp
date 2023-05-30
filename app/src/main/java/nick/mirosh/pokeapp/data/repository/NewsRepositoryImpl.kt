package nick.mirosh.pokeapp.data.repository

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import nick.mirosh.pokeapp.database.AppDatabase
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

    override var articles: Flow<List<Article>> = dao.getAllArticles().map {
        it.asDomainModel()
    }

    override suspend fun getNewsList() {
        withContext(Dispatchers.IO) {
            try {
                val networkArticles = newsDataSource?.getHeadlines()
                if (networkArticles != null) {
                    dao.insertAll(networkArticles.map {
                        it.asDatabaseArticle()
                    })

                    articles = MutableStateFlow(networkArticles.map {
                        it.asDomainModel()
                    })
                }
            } catch (e: Exception) {
                articles = dao.getAllArticles().map {
                    it.asDomainModel()
                }
            }
        }
    }
    override suspend fun getFavoriteArticles() {
        withContext(Dispatchers.IO) {
            articles = dao.getLikedArticles().map {
                it.asDomainModel()
            }
        }
    }
    override suspend fun saveLikedArticle(article: Article) {
        withContext(Dispatchers.IO) {
            dao.insert(article.asDatabaseModel())
        }
    }
}