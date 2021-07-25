package vamsee.application.anime.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import vamsee.application.anime.modal.Anime
import vamsee.application.anime.modal.PosterList

interface SimpleApi {
    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("top/anime/1/tv")
    suspend fun getAnime(): Response<Anime>

    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("top/anime/1/airing")
    suspend fun getTopAiring(): Response<Anime>

    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("top/anime/1/movie")
    suspend fun getTopMovies(): Response<Anime>

    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("top/anime/1/upcoming")
    suspend fun getTopUpcoming(): Response<Anime>

    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("anime/{id}/pictures")
    suspend fun getAnimePosters(
        @Path(value = "id") id: Long
    ): Response<PosterList>


    @Headers(
        "x-rapidapi-key: 299a2003d8mshb2d9b905fd9b742p1bd261jsn4fd206e66217",
        "x-rapidapi-host: jikan1.p.rapidapi.com"
    )
    @GET("search/anime")
    suspend fun searchAnime(
        @Query("q") name: String,
        @Query("type") type: String = "anime"
    ): Response<Anime>

}