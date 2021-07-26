package vamsee.application.anime

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import vamsee.application.anime.adapter.ImageSliderAdapter
import vamsee.application.anime.databinding.FragmentDescriptionBinding
import vamsee.application.anime.modal.Poster
import vamsee.application.anime.repository.Repository
import kotlin.properties.Delegates

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private var mal_id by Delegates.notNull<Long>()
    private lateinit var title: String
    private var score by Delegates.notNull<Float>()
    private var episodes by Delegates.notNull<Long>()
    private lateinit var type: String

    private val autoSlider: Handler = Handler()
    private lateinit var viewModel: MainViewModel
    private val posterAdapter by lazy { context?.let { ImageSliderAdapter(it, binding.viewPager) } }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString("title").toString()
            mal_id = it.getLong("mal_id")
            score = it.getFloat("score")
            episodes = it.getLong("episodes")
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAnimePosters(mal_id)
        viewModel.posterList.observe(viewLifecycleOwner, Observer { response ->
           if(response.isSuccessful){
               response.body()?.let { posterAdapter?.setPoster(
                   if (it.pictures.size > 5){
                       it.pictures.take(5) as ArrayList<Poster>
                   }
                   else{
                       it.pictures as ArrayList<Poster>
                   }
               )
                    }
                Log.d("Response", response.body().toString())
            }
            else{
                Toast.makeText(context, "unable to load posters!! ${response.errorBody()}",
                    Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.searchAnime(title)
        viewModel.searchedAnime.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                response.body()?.searchResults?.forEach lit@{
                    if (it.mal_id == mal_id){
                        binding.AnimeDescription.text = it.synopsis
                        return@lit
                    }
                }
            }
            else{
                Toast.makeText(context, "unable to search anime !!", Toast.LENGTH_SHORT).show()
            }

        })

        binding.animeTitle.text = title
        binding.AnimeRating.text = "Rating: ${score}"
        binding.animeType.text = type
        binding.episodeCount.text = "Episodes : ${episodes}"

        val viewPager = binding.viewPager
        viewPager.adapter = posterAdapter
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(3)
        viewPager.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(0))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.95f + r * 0.05f
        }

        val sliderRunnable: Runnable = Runnable {
            viewPager.currentItem = viewPager.currentItem + 1

        }

        viewPager.setPageTransformer(compositePageTransformer)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                autoSlider.removeCallbacks(sliderRunnable)
                autoSlider.postDelayed(sliderRunnable, 2000)
            }
        }
        )






    }

}