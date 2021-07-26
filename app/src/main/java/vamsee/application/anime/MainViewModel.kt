package vamsee.application.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import vamsee.application.anime.modal.Anime
import vamsee.application.anime.modal.PosterList
import vamsee.application.anime.repository.Repository

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse: MutableLiveData<Response<Anime>> = MutableLiveData()
    val topMovies: MutableLiveData<Response<Anime>> = MutableLiveData()
    val topAiring: MutableLiveData<Response<Anime>> = MutableLiveData()
    val topUpcoming: MutableLiveData<Response<Anime>> = MutableLiveData()
    val posterList: MutableLiveData<Response<PosterList>> = MutableLiveData()
    val searchedAnime: MutableLiveData<Response<Anime>> = MutableLiveData()
    val promos: MutableLiveData<Response<Anime>> = MutableLiveData()
    val recommendations: MutableLiveData<Response<Anime>> = MutableLiveData()

    fun getAnime(){
        viewModelScope.launch {
            val response = repository.getAnime()
            myResponse.value = response
        }
    }

    fun getTopMovies(){
        viewModelScope.launch {
            val response = repository.getTopMovies()
            topMovies.value = response
        }
    }

    fun getTopAiring(){
        viewModelScope.launch {
            val response = repository.getTopAiring()
            topAiring.value = response
        }
    }

    fun getTopUpcoming(){
        viewModelScope.launch {
            val response = repository.getTopUpcoming()
            topUpcoming.value = response
        }
    }

    fun getAnimePosters(id: Long){
        viewModelScope.launch {
            val response = repository.getAnimePosters(id)
            posterList.value = response
        }
    }

    fun searchAnime(name: String){
        viewModelScope.launch {
            val response = repository.searchAnime(name)
            searchedAnime.value = response
        }
    }

    fun getPromos(id: Long){
        viewModelScope.launch {
            val response = repository.getPromos(id)
            promos.value = response
        }
    }

    fun getRecommendations(id: Long){
        viewModelScope.launch {
            val response = repository.getRecommendations(id)
            recommendations.value = response
        }
    }
}