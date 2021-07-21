package vamsee.application.anime

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import vamsee.application.anime.repository.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAnime()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.request_hash.toString())
                Log.d("Response", response.body()?.image_url.toString())
            }
            else{
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}