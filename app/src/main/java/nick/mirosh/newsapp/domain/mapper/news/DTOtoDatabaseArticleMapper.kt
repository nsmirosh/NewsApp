package nick.mirosh.newsapp.domain.mapper.news

import nick.mirosh.newsapp.domain.mapper.BaseMapper
import nick.mirosh.newsapp.entity.ArticleDTO
import nick.mirosh.newsapp.entity.DatabaseArticle
import nick.mirosh.newsapp.entity.asDatabaseModel
import javax.inject.Inject

class DTOtoDatabaseArticleMapper @Inject constructor() : BaseMapper<List<ArticleDTO>, List<DatabaseArticle>>() {
    override fun map(data: List<ArticleDTO>) =
        data.map {
            it.asDatabaseModel()
        }
}