package vamsee.application.anime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vamsee.application.anime.R
import vamsee.application.anime.modal.AnimeSeries

class MyAdapter(private val context: Context, private val listner: OnItemClick): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var animeList = emptyList<AnimeSeries>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.animeName)
        val poster = itemView.findViewById<ImageView>(R.id.animePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posters, parent, false)
        val viewHolder = MyViewHolder(view)

        view.setOnClickListener{
            listner.onAnimeClick(animeList[viewHolder.adapterPosition], view)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = animeList[position].title
        Glide.with(context).load(animeList[position].image_url).into(holder.poster)
    }

    override fun getItemCount(): Int {
        return animeList.size
    }


    fun setData(newList: List<AnimeSeries>){
        animeList = newList
        notifyDataSetChanged()
    }
}

interface OnItemClick{
    fun onAnimeClick(item: AnimeSeries, view: View)
}