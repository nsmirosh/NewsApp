package nick.mirosh.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nick.mirosh.newsapp.data.DataState
import nick.mirosh.newsapp.database.ArticleDao
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.entity.Article
import nick.mirosh.newsapp.entity.asDatabaseModel
import nick.mirosh.newsapp.utils.logStackTrace
import javax.inject.Inject


const val tag = "NewsRepository"

interface NewsRepository {
    suspend fun refreshNews(): DomainState<List<Article>>
    suspend fun getFavoriteArticles(): Flow<DataState<List<Article>>>
    suspend fun updateArticle(article: Article): DomainState<Article>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource? = null,
    private val newsLocalDataSource: ArticleDao,
    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
    private val dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
) : NewsRepository {
    override suspend fun refreshNews(): DomainState<List<Article>> {
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
        flow {
            try {
                emit(
                    DataState.Success(
                        databaseToDomainArticleMapper.map(
                            newsLocalDataSource.getLikedArticles()
                        )
                    )
                )
            } catch (e: Exception) {
                e.logStackTrace(tag)
                emit(DataState.Error("Error fetching favorite articles"))
            }
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



