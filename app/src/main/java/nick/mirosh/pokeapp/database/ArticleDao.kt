package nick.mirosh.pokeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nick.mirosh.pokeapp.entity.DatabaseArticle

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<DatabaseArticle>>


    @Query("SELECT * FROM articles WHERE liked = 1")
    fun getLikedArticles(): Flow<List<DatabaseArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<DatabaseArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: DatabaseArticle)

    @Delete
    fun delete(article: DatabaseArticle)
}