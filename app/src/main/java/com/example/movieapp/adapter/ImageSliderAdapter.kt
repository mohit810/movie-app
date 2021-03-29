package com.example.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R

class ImageSliderAdapter(
    internal var context: Context,
    internal var sliderimages: ArrayList<String>
) : RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumnail: ImageView

        init {
            thumnail = itemView.findViewById(R.id.sliderImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(context)
            .inflate(R.layout.item_container_slider_image, parent, false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return sliderimages.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(sliderimages[position])
            .fitCenter()
            .into(holder.thumnail);
    }

    fun addData(temp: ArrayList<String>) {
        this.sliderimages = temp
    }
}