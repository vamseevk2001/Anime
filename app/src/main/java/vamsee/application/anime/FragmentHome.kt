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
import androidx.viewpager2.widget.ViewPager2
import vamsee.application.anime.adapter.AnimePromosAdapter
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
    private val promoAdapter by lazy { context?.let { AnimePromosAdapter(it) } }

    private lateinit var topRated: RecyclerView
    private lateinit var trending: RecyclerView
    private lateinit var topMovies: RecyclerView
    private lateinit var topUpcoming: RecyclerView

    private lateinit var promos: ViewPager2
    var promoId: ArrayList<AnimeSeries> = arrayListOf()
    var promoList: ArrayList<AnimeSeries> = arrayListOf()

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
        promos = binding.promos
        setUpRecyclerViews()
        setUpAdapters()
        //setUpPromos()
    }

    fun setUpRecyclerViews() {
        topRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topRated.adapter = topRatedAdapter

        topMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topMovies.adapter = topMoviesAdapter

        topUpcoming.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topUpcoming.adapter = topUpcomingAdapter

        trending.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        trending.adapter = trendingNowAdapter
    }

    fun setUpAdapters() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAnime()
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let {
                    topRatedAdapter?.setData(it.top)
//                    it.top.forEach {
//                        promoList.add(it)
//                    }
                }
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getTopAiring()
        viewModel.topAiring.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { trendingNowAdapter?.setData(it.top) }
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getTopMovies()
        viewModel.topMovies.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { topMoviesAdapter?.setData(it.top) }
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getTopUpcoming()
        viewModel.topUpcoming.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.top?.get(0)?.title.toString())
                response.body()?.let { topUpcomingAdapter?.setData(it.top) }
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        })


        promoId.forEach { it ->
            viewModel.getPromos(it.mal_id)
            viewModel.promos.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.promo?.forEach {
                        promoList.add(it)
                    }
                    Log.d("anime", response.body()?.promo.toString())

                } else {
                    Log.d("animePromo", response.errorBody().toString())
                    Toast.makeText(context, "error ${response.errorBody()}", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }

        Log.d("AnimeList", promoList.toString())

        promoList = arrayListOf(
            AnimeSeries(
                6702, "Fairy Tail",
                "https://9tailedkitsune.com/wp-content/uploads/2020/08/fairy-tail-koei-tecmo-videogioco-pdvg-800x445.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=BaIWwk-sAlw"
            ),
            AnimeSeries(
                11061, "Hunter x Hunter",
                "https://occ-0-1722-1723.1.nflxso.net/dnm/api/v6/E8vDc_W8CLv7-yMQu8KMEC7Rrr8/AAAABRpjstglCxdnk-N1buyJJEUpI0tMn28yj9-UXnUCY40nWh3L-2osrAa5WWod7W1UKeDBcK-T__tqjqiKSXZCPm48TEGe.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=d6kBeJjTGnY&t=12s"
            ),
            AnimeSeries(
                2223, "Dragon Ball Z",
                "https://images.nintendolife.com/8ce294add2bb3/dragon-ball-z-kakarot.original.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=sxufB6DxXk0"
            ),
            AnimeSeries(
                918, "Gintama",
                "https://otakuusamagazine.com/wp-content/uploads/2020/09/gintama-final-sheets.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=YQC3ot0IjiA&t=1s"
            ),
            AnimeSeries(
                1735, "Naruto: Shippuden",
                "https://static.gojinshi.com/wp-content/uploads/2020/07/Naruto-Shippuden-Filler-Episodes-List.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=thh7bVCgDHs"
            ),
            AnimeSeries(
                3588, "Soul Eater",
                "https://nefariousreviews.files.wordpress.com/2019/05/soul-eater-featured.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=pcAigv_MQAo"
            ),
            AnimeSeries(
                269, "Bleach",
                "https://thenewsfetcher.com/wp-content/uploads/2020/09/bleach-1212912.jpeg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=oZ67d9XSjFs"
            ),
            AnimeSeries(
                34572, "Black Clover",
                "https://theawesomeone.com/wp-content/uploads/2020/12/black-clover-anime.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/embed/vUjAxk1qYzQ?enablejsapi=1&wmode=opaque&autoplay=1"
            ),
            AnimeSeries(
                31964, "My Hero Academia",
                "https://thecinemaholic.com/wp-content/uploads/2021/03/My-Hero-Academia-2.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=wIb3nnOeves"
            ),
            AnimeSeries(
                24833, "Assasination Classroom",
                "https://www.tvovermind.com/wp-content/uploads/2018/11/Assasination-Classroom.jpg",
                "", 9, 9.8f, "", "https://www.youtube.com/watch?v=kgNkGohA20k"
            )
            )

        promoAdapter?.setPromo(promoList)
        promos.adapter = promoAdapter
        promos.offscreenPageLimit = 3
        promos.getChildAt(3)
        promos.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    override fun onAnimeClick(item: AnimeSeries, view: View) {
        val action = FragmentHomeDirections.actionFragmentHomeToDescriptionFragment(
            item.mal_id,
            item.title,
            item.episodes,
            item.type,
            item.score
        )
        view.findNavController().navigate(action)
    }





}