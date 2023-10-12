package nick.mirosh.newsapp.domain.mapper.news

import nick.mirosh.newsapp.domain.mapper.BaseMapper
import nick.mirosh.newsapp.entity.Article
import nick.mirosh.newsapp.entity.DatabaseArticle
import nick.mirosh.newsapp.entity.asDatabaseModel
import javax.inject.Inject

class DomaintoDatabaseArticleMapper @Inject constructor() : BaseMapper<List<Article>, List<DatabaseArticle>>() {
    override fun map(data: List<Article>) =
        data.map {
            it.asDatabaseModel()
        }
}