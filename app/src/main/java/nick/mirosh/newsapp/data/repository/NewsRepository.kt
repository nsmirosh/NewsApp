package nick.mirosh.newsapp.data.repository

import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.models.asDatabaseModel
import nick.mirosh.newsapp.utils.logStackTrace
import javax.inject.Inject


const val tag = "NewsRepository"

interface NewsRepository {
    suspend fun getNewsArticles(): Resource<List<Article>>
    suspend fun getFavoriteArticles(): Resource<List<Article>>
    suspend fun updateArticle(article: Article): Resource<Article>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource? = null,
    private val newsLocalDataSource: ArticleDao,
    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
    private val dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(): Resource<List<Article>> {
        return try {
            newsRemoteDataSource?.getHeadlines()?.let {
                newsLocalDataSource.insertAll(dtoToDatabaseArticleMapper.map(it))
            }
            Resource.Success(
                databaseToDomainArticleMapper.map(getAllArticlesFromDb())
            )
        } catch (e: Exception) {
            e.logStackTrace(tag)
            Resource.Error(ErrorType.General)
        }
    }


    override suspend fun getFavoriteArticles() =
        try {
            Resource.Success(
                databaseToDomainArticleMapper.map(
                    newsLocalDataSource.getLikedArticles()
                )
            )
        } catch (e: Exception) {
            e.logStackTrace(tag)
            Resource.Error(ErrorType.General)
        }

    override suspend fun updateArticle(article: Article) =
        try {
            val updatedRowId = newsLocalDataSource.insert(article.asDatabaseModel())
            if (updatedRowId != -1L)
                Resource.Success(article)
            else
                Resource.Error(ErrorType.General)
        } catch (e: Exception) {
            e.logStackTrace(tag)
            Resource.Error(ErrorType.General)
        }

    private suspend fun getAllArticlesFromDb() =
        newsLocalDataSource.getAllArticles()
            .sortedByDescending { it.liked }
}



