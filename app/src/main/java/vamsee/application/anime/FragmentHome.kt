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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vamsee.application.anime.adapter.MyAdapter
import vamsee.application.anime.adapter.OnItemClick
import vamsee.application.anime.databinding.FragmentHomeBinding
import vamsee.application.anime.modal.AnimeSeries
import vamsee.application.anime.repository.Repository

class FragmentHome : Fragment(), OnItemClick {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    private val topRatedAdapter by lazy { context?.let { MyAdapter(it, this) } }
    private val trendingNowAdapter by lazy { context?.let { MyAdapter(it, this) } }
    private val topMoviesAdapter by lazy { context?.let { MyAdapter(it, this) } }
    private val topUpcomingAdapter by lazy { context?.let { MyAdapter(it, this) } }

    private lateinit var topRated: RecyclerView
    private lateinit var trending: RecyclerView
    private lateinit var topMovies: RecyclerView
    private lateinit var topUpcoming: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        topRated = binding.topRated
        topMovies = binding.topMovies
        topUpcoming = binding.topUpcoming
        trending = binding.trendingNow
        setUpRecyclerViews()
        setUpAdapters()
    }

    fun setUpRecyclerViews(){
        topRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topRated.adapter = topRatedAdapter

        topMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topMovies.adapter = topMoviesAdapter

        topUpcoming.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topUpcoming.adapter = topUpcomingAdapter

        trending.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        trending.adapter = trendingNowAdapter
    }

    fun setUpAdapters(){
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAnime()
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { topRatedAdapter?.setData(it.top) }
            }
            else{
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })


        viewModel.getTopAiring()
        viewModel.topAiring.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { trendingNowAdapter?.setData(it.top) }
            }
            else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getTopMovies()
        viewModel.topMovies.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { topMoviesAdapter?.setData(it.top) }
            }
            else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getTopUpcoming()
        viewModel.topUpcoming.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { topUpcomingAdapter?.setData(it.top) }
            }
            else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onAnimeClick(item: AnimeSeries, view: View) {
        val action = FragmentHomeDirections.actionFragmentHomeToDescriptionFragment(item.mal_id, item.title)
        view.findNavController().navigate(action)
    }

}