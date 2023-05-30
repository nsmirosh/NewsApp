package nick.mirosh.newsapp.entity

data class ApiResponse<T> (
    val status: String,
    val articles: List<T>
)