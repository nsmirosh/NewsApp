package nick.mirosh.newsapp.domain

/**
 * Represents a resource that can either contain successful data or an error.
 *
 * This is a sealed class, which allows us to represent restricted class hierarchies.
 * When used as a return type, `Resource` class can help to manage different states like success or failure.
 */
sealed class Resource<out T> {
    /**
     * Represents successful data.
     *
     * @property data Data of generic type T.
     */
    data class Success<out T>(val data: T) : Resource<T>()

    /**
     * Represents a failure.
     *
     * @property error The type of error, represented by ErrorType.
     */
    data class Error(val error: ErrorType) : Resource<Nothing>()
}
