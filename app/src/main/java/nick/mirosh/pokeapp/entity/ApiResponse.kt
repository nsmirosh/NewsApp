package nick.mirosh.pokeapp.entity

data class ApiResponse<T> (
    val status: String,
    val articles: List<T>
)