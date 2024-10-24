package nick.mirosh.newsapp.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import nick.mirosh.newsapp.data.models.ApiResponse
import nick.mirosh.newsapp.data.models.ArticleDTO

const val ENDPOINT = "top-headlines"
const val COUNTRY_PARAM = "country"
class NewsRemoteDataSource(private val client: HttpClient) {
    suspend fun getHeadlines(country: String): List<ArticleDTO> {
        return client.get(ENDPOINT) {
            url { parameters.append(COUNTRY_PARAM, country) }
        }.body<ApiResponse>().articles!!
    }
}