package nick.mirosh.pokeapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleDTO(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    val url: String? = null,
    @ColumnInfo(name = "url_to_image") val urlToImage: String? = null,
)
