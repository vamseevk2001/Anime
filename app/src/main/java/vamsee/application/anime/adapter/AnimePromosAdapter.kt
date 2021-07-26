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

class AnimePromosAdapter(private val context: Context): RecyclerView.Adapter<AnimePromosAdapter.PromosViewHolder>() {

    var promoList: List<AnimeSeries> = emptyList()

    inner class PromosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.animeNamePromo)
        val promoImg: ImageView = itemView.findViewById(R.id.promoImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.promos, parent, false)
        val viewHolder = PromosViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PromosViewHolder, position: Int) {
        holder.name.text = promoList[position].title
        Glide.with(context).load(promoList[position].image_url).into(holder.promoImg)
    }

    override fun getItemCount(): Int {
        return promoList.size
    }

    fun setPromo(list: List<AnimeSeries>){
        promoList = list
        notifyDataSetChanged()
    }
}