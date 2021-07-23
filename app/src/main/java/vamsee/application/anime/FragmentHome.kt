package vamsee.application.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.anime.adapter.MyAdapter
import vamsee.application.anime.databinding.FragmentHomeBinding
import vamsee.application.anime.modal.AnimeSeries
import vamsee.application.anime.repository.Repository

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { context?.let { MyAdapter(it) } }
    private lateinit var topRated: RecyclerView
    private var animeList: List<AnimeSeries> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAnime()
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { myAdapter?.setData(it.top) }
            }
            else{
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })
        topRated = binding.topRated
        setUpRecyclerView()
    }

    fun setUpRecyclerView(){
        topRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topRated.adapter = myAdapter
    }


}