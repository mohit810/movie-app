package com.example.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.helpers.retrofit.dataclass.Genres

class GenreAdapter(
    internal var context: Context,
    internal var genres: List<Genres>
) : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var genre: TextView

        init {
            genre = itemView.findViewById(R.id.genre_txt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(context)
            .inflate(R.layout.genre_item, parent, false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.genre.text = genres[position].name
    }
}