package vamsee.application.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import vamsee.application.anime.modal.AnimeSeries
import vamsee.application.anime.repository.Repository

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse: MutableLiveData<Response<AnimeSeries>> = MutableLiveData()

    fun getAnime(){
        viewModelScope.launch {
            val response = repository.getAnime()
            myResponse.value = response
        }
    }
}