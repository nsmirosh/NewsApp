package nick.mirosh.pokeapp.data.repository

import nick.mirosh.pokeapp.entity.ArticleDTO

interface NewsDataSource {


    suspend fun getHeadlines(): List<ArticleDTO>?
}