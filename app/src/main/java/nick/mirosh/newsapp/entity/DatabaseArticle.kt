package nick.mirosh.newsapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class DatabaseArticle(
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    val title: String,
    @PrimaryKey val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    val liked: Boolean = false,
)


fun DatabaseArticle.asDomainModel() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)


fun List<DatabaseArticle>.asDomainModel(): List<Article> {
    return map {
        Article(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
        )
    }
}
