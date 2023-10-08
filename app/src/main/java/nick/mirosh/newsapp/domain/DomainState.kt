package nick.mirosh.newsapp.domain

sealed class DomainState<out T> {
    data class Success<out T>(val data: T) : DomainState<T>()
    data object Loading : DomainState<Nothing>()
    data object Empty : DomainState<Nothing>()
    data class Error(val message: String?, val statusCode: Int? = null) : DomainState<Nothing>()
}
