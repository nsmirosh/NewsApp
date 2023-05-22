package nick.mirosh.pokeapp.entity

data class ArticleDTO(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String,
    val url: String,
    val urlToImage: String,
)
