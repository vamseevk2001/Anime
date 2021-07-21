package vamsee.application.anime.repository

import retrofit2.Response
import vamsee.application.anime.api.RetrofitInstance
import vamsee.application.anime.modal.AnimeSeries

class Repository {
    suspend fun getAnime(): Response<AnimeSeries> {
        return RetrofitInstance.api.getAnime()
    }
}