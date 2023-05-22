package nick.mirosh.pokeapp.networking

import nick.mirosh.pokeapp.entity.ApiResponse
import nick.mirosh.pokeapp.entity.ArticleDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    fun getHeadlines(@Query("country") country: String): Call<ApiResponse<ArticleDTO>>

}