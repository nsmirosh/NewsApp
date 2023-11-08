package nick.mirosh.newsapp.domain.feed.model

import nick.mirosh.newsapp.data.models.ArticleDTO
import nick.mirosh.newsapp.data.models.DatabaseArticle

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val liked: Boolean = false,
)

fun Article.asDTO() = ArticleDTO(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
)

fun Article.asDatabaseModel() = DatabaseArticle(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)
