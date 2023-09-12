package nick.mirosh.newsapp.networking

import nick.mirosh.newsapp.data.entities.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("recipes/random")
    fun getHeadlines(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int,
    ): Call<RecipesResponse>

}