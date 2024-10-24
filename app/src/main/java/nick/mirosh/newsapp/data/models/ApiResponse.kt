package nick.mirosh.newsapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleDTO>? = null
)