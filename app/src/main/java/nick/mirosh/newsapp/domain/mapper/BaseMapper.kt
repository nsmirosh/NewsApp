package nick.mirosh.newsapp.domain.mapper

abstract class BaseMapper<in Input, out Output> {
    abstract fun map(data: Input): Output
}
