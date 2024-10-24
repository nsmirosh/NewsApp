package nick.mirosh.newsapp.data.networking

import nick.mirosh.newsapp.data.models.ApiResponse
import nick.mirosh.newsapp.data.models.ArticleDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines")
    suspend fun getHeadlines(@Query("country") country: String): ApiResponse

}