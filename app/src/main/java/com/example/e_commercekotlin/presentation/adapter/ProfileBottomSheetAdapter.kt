package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.data.model.Followings
import com.example.e_commercekotlin.databinding.ProfileBottomSheetListBinding
import com.example.e_commercekotlin.Util.dp

class ProfileBottomSheetAdapter : RecyclerView.Adapter<ProfileBottomSheetAdapter.MyViewHolder>() {

    private var followingList: List<Followings> = listOf()

    fun setFollowingList(followingList: List<Followings>) {
        this.followingList = followingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ProfileBottomSheetListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = followingList[position]

        holder.binding.bottomSheetStoreImage.image.setImageResource(currentItem.getFollowingImage())
        holder.binding.bottomSheetFollowingName.text = currentItem.getFollowingName()
        holder.binding.bottomSheetFollowingType.text = currentItem.getFollowingType()

        holder.binding.profileBottomSheetFollowingButton.followingButtonTv.text = "Unfollow"
        holder.binding.profileBottomSheetFollowingButton.followingButtonTv.setPadding(
            7.dp,
            4.dp,
            7.dp,
            4.dp
        )

        holder.binding.profileBottomSheetFollowingButton.root.setOnClickListener {
            val newText = if (holder.binding.profileBottomSheetFollowingButton.followingButtonTv.text == "Follow") "Unfollow" else "Follow"
            holder.binding.profileBottomSheetFollowingButton.followingButtonTv.text = newText
        }
    }

    override fun getItemCount(): Int {
        return followingList.size
    }

    class MyViewHolder(val binding: ProfileBottomSheetListBinding) : RecyclerView.ViewHolder(binding.root)
}
