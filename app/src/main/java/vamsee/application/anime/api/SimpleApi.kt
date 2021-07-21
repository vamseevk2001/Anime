package vamsee.application.anime.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import vamsee.application.anime.modal.AnimeSeries

interface SimpleApi {
    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("top/anime/1/upcoming")
    suspend fun getAnime(): Response<AnimeSeries>
}