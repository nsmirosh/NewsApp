package nick.mirosh.newsapp.entity

data class Article(
    val uid: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val liked: Boolean = false,
)

fun Article.asDatabaseModel() = DatabaseArticle(
    uid = uid,
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)
