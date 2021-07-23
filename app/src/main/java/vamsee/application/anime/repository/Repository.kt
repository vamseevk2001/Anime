package vamsee.application.anime.repository

import retrofit2.Response
import vamsee.application.anime.api.RetrofitInstance
import vamsee.application.anime.modal.Anime

class Repository {
    suspend fun getAnime(): Response<Anime> {
        return RetrofitInstance.api.getAnime()
    }

    suspend fun getTopMovies(): Response<Anime> {
        return RetrofitInstance.api.getTopMovies()
    }

    suspend fun getTopAiring(): Response<Anime> {
        return RetrofitInstance.api.getTopAiring()
    }

    suspend fun getTopUpcoming(): Response<Anime> {
        return RetrofitInstance.api.getTopUpcoming()
    }

}