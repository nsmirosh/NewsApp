package nick.mirosh.newsapp.domain.mapper.news

import nick.mirosh.newsapp.domain.mapper.BaseMapper
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.data.models.DatabaseArticle
import nick.mirosh.newsapp.data.models.asDomainModel
import javax.inject.Inject

class DatabaseToDomainArticleMapper @Inject constructor() : BaseMapper<List<DatabaseArticle>, List<Article>>() {
    override fun map(data: List<DatabaseArticle>) =
        data.map {
            it.asDomainModel()
        }
}