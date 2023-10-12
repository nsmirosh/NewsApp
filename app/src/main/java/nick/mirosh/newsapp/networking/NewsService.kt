package nick.mirosh.newsapp.networking

import nick.mirosh.newsapp.entity.ApiResponse
import nick.mirosh.newsapp.entity.ArticleDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("top-headlines")
    suspend fun getHeadlines(@Query("country") country: String): ApiResponse<ArticleDTO>

}