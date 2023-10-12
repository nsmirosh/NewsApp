package nick.mirosh.newsapp.data.models

data class ApiResponse<T> (
    val status: String,
    val articles: List<T>
)