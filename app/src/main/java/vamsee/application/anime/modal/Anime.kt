package vamsee.application.anime.modal

import com.google.gson.annotations.SerializedName

data class Anime(
    val top: List<AnimeSeries>,
    @SerializedName("results")
    val searchResults: List<AnimeSeries>,
    val promo: List<AnimeSeries>,
    val recommendations: List<AnimeSeries>
)