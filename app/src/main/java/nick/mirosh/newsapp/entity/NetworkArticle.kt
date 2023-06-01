package nick.mirosh.newsapp.entity

data class NetworkArticle(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
)
fun NetworkArticle.asDatabaseArticle() = DatabaseArticle(
    author = author.orEmpty(),
    content = content.orEmpty(),
    description = description.orEmpty(),
    publishedAt = publishedAt.orEmpty(),
    title = title.orEmpty(),
    url = url.orEmpty(),
    urlToImage = urlToImage.orEmpty(),
)

