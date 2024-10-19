package nick.mirosh.newsapp.domain.mapper.news

import nick.mirosh.newsapp.domain.mapper.BaseMapper
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.data.models.DatabaseArticle
import nick.mirosh.newsapp.data.models.asDomainModel

class DatabaseToDomainArticleMapper : BaseMapper<List<DatabaseArticle>, List<Article>>() {
    override fun map(data: List<DatabaseArticle>) =
        data.map {
            it.asDomainModel()
        }
}