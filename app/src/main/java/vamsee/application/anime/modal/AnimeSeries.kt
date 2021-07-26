package vamsee.application.anime.modal

import com.google.gson.annotations.SerializedName

data class AnimeSeries(
    val mal_id: Long,
    val title: String,
    val image_url: String,
    val synopsis: String,
    val episodes: Long,
    val score: Float,
    val type: String,
    @SerializedName("video_url")
    val promoUrl: String
)