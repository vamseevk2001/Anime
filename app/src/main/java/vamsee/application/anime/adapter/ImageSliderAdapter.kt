package vamsee.application.anime.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import vamsee.application.anime.R
import vamsee.application.anime.modal.Poster

class ImageSliderAdapter(private val context: Context, private val viewPager2: ViewPager2): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    //private val viewPager2: ViewPager2? = null
    private var posterList: ArrayList<Poster> = arrayListOf()
    private val  runnable: Runnable = Runnable {
        posterList.addAll(posterList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val poster = itemView.findViewById<RoundedImageView>(R.id.posterSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_anime_poster, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val url: String = posterList?.get(position).poster
        Glide.with(context).load(url).into(holder.poster)
        Log.d("Vamsee", posterList?.get(position).poster)
        if (position == posterList.size-2){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return posterList.size
    }

    fun setPoster(list: ArrayList<Poster>){
        posterList = list
        notifyDataSetChanged()
    }



}