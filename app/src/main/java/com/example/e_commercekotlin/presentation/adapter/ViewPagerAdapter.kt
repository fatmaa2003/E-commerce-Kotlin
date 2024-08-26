package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.ViewpagerItemBinding
import com.squareup.picasso.Picasso

class ViewPagerAdapter(private val imageList: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewpagerItemBinding.bind(itemView)
        val imageView = binding.adImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewpager_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        Picasso.get()
            .load(imageList[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
