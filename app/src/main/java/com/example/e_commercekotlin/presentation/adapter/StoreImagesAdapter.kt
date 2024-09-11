package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.StoreImages
import com.example.e_commercekotlin.data.model.Stores
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel


class StoreImagesAdapter() : RecyclerView.Adapter<StoreImagesAdapter.MyViewHolder>() {

    private lateinit var storeImagesList : Stores

    fun setStoreImagesList(storeImagesList : Stores){
        this.storeImagesList = storeImagesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.store_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = storeImagesList[position]

        handleRoundedImageRadius(position, holder)
    }

    private fun handleRoundedImageRadius(
        position: Int,
        holder: MyViewHolder
    ) {
        val shapeAppearanceModel = when (position) {
            0 -> {
                ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 100f)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, 100f)
                    .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                    .build()
            }

            storeImagesList.size - 1 -> {
                ShapeAppearanceModel.builder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                    .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                    .setTopRightCorner(CornerFamily.ROUNDED, 100f)
                    .setBottomRightCorner(CornerFamily.ROUNDED, 100f)
                    .build()
            }

            else -> {
                ShapeAppearanceModel.builder()
                    .setAllCorners(CornerFamily.ROUNDED, 0f)
                    .build()
            }
        }

        holder.storeImage.shapeAppearanceModel = shapeAppearanceModel
    }


    override fun getItemCount(): Int {
        return storeImagesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeImage: ShapeableImageView = itemView.findViewById(R.id.store_images)
    }
}
