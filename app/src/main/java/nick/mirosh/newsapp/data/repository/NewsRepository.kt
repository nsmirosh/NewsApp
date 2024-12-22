package nick.mirosh.newsapp.data.repository

import android.util.Log
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.model.asDatabaseModel

const val TAG = "NewsRepository"

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: ArticleDao,
    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
    private val dtoToDatabaseArticleMapper: DTOtoDatabaseArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(country: String): Result<List<Article>> {
        return try {
            newsRemoteDataSource.getHeadlines(country).let {
                newsLocalDataSource.insertAll(dtoToDatabaseArticleMapper.map(it))
            }
            Result.Success(
                databaseToDomainArticleMapper.map(getAllArticlesFromDb())
            )
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            Result.Error(ErrorType.General)
        }
    }


    override fun getFavoriteArticles() =
        newsLocalDataSource.getLikedArticles().map { articles ->
            if (articles != null) {
                Result.Success(databaseToDomainArticleMapper.map(articles))
            } else {
                Result.Error(ErrorType.General)
            }
        }

    override suspend fun updateArticle(article: Article) =
        try {
            val updatedRowId = newsLocalDataSource.insert(article.asDatabaseModel())
            if (updatedRowId != -1L)
                Result.Success(article)
            else
                Result.Error(ErrorType.General)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            Result.Error(ErrorType.General)
        }

    private suspend fun getAllArticlesFromDb() =
        newsLocalDataSource.getAllArticles()
            .sortedByDescending { it.liked }
}



