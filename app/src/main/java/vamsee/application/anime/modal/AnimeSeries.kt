package vamsee.application.anime.modal

data class AnimeSeries(
    val mal_id: Long,
    val title: String,
    val image_url: String,
    val synopsis: String,
    val episodes: Long,
    val score: Float,
    val type: String
)