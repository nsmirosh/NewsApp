package nick.mirosh.pokeapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nick.mirosh.pokeapp.entity.ArticleDTO

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAll(): List<ArticleDTO>

    @Insert
    fun insertAll(vararg articles: ArticleDTO)

    @Delete
    fun delete(article: ArticleDTO)
}