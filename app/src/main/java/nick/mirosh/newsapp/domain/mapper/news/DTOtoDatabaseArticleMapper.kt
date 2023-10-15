package nick.mirosh.newsapp.domain.mapper.news

import nick.mirosh.newsapp.domain.mapper.BaseMapper
import nick.mirosh.newsapp.data.models.ArticleDTO
import nick.mirosh.newsapp.data.models.DatabaseArticle
import nick.mirosh.newsapp.data.models.asDatabaseModel
import nick.mirosh.newsapp.domain.models.asDatabaseModel
import javax.inject.Inject

class DTOtoDatabaseArticleMapper @Inject constructor() : BaseMapper<List<ArticleDTO>, List<DatabaseArticle>>() {
    override fun map(data: List<ArticleDTO>) =
        data.map {
            it.asDatabaseModel()
        }
}