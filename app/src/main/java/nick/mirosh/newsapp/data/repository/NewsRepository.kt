package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.models.asDatabaseModel
import nick.mirosh.newsapp.utils.logStackTrace
import javax.inject.Inject


const val tag = "NewsRepository"

interface NewsRepository {
    suspend fun getNewsArticles(): DomainState<List<Article>>
    suspend fun getFavoriteArticles(): DomainState<List<Article>>
    suspend fun updateArticle(article: Article): DomainState<Article>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource? = null,
    private val newsLocalDataSource: ArticleDao,
    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
    private val dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(): DomainState<List<Article>> {
        return try {
            newsRemoteDataSource?.getHeadlines()?.let {
                newsLocalDataSource.insertAll(dtoToDatabaseArticleMapper.map(it))
            }
            DomainState.Success(
                databaseToDomainArticleMapper.map(getAllArticlesFromDb())
            )
        } catch (e: Exception) {
            e.logStackTrace(tag)
            DomainState.Error("Error fetching headlines")
        }
    }


    override suspend fun getFavoriteArticles() =
        try {
            DomainState.Success(
                databaseToDomainArticleMapper.map(
                    newsLocalDataSource.getLikedArticles()
                )
            )
        } catch (e: Exception) {
            e.logStackTrace(tag)
            DomainState.Error("Error fetching favorite articles")
        }

    override suspend fun updateArticle(article: Article) =
        try {
            val updatedRowId = newsLocalDataSource.insert(article.asDatabaseModel())
            if (updatedRowId != -1L)
                DomainState.Success(article)
            else
                DomainState.Error("Error updating article")
        } catch (e: Exception) {
            e.logStackTrace(tag)
            DomainState.Error("Error updating article")
        }

    private suspend fun getAllArticlesFromDb() =
        newsLocalDataSource.getAllArticles()
            .sortedByDescending { it.liked }
}



