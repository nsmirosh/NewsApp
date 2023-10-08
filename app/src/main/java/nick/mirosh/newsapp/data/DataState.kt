package nick.mirosh.newsapp.data

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data object Loading : DataState<Nothing>()
    data object Empty : DataState<Nothing>()
    data class Error(val message: String?, val statusCode: Int? = null) : DataState<Nothing>()
}
