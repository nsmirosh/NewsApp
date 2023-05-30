package nick.mirosh.newsapp.networking

import nick.mirosh.newsapp.entity.ApiResponse
import nick.mirosh.newsapp.entity.NetworkArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    fun getHeadlines(@Query("country") country: String): Call<ApiResponse<NetworkArticle>>

}