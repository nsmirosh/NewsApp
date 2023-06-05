package nick.mirosh.newsapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nick.mirosh.newsapp.entity.DatabaseArticle

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles():  List<DatabaseArticle>

    @Query("SELECT * FROM articles WHERE liked = 1")
    fun getLikedArticles(): List<DatabaseArticle>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(articles: List<DatabaseArticle>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: DatabaseArticle)

    @Delete
    fun delete(article: DatabaseArticle)
}