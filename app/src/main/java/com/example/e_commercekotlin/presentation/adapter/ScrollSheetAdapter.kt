package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.ItemOptionBinding

class ScrollSheetAdapter(private val options: List<String>) :
    RecyclerView.Adapter<ScrollSheetAdapter.OptionViewHolder>() {

    var selectedPosition = -1

    inner class OptionViewHolder(private val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(option: String, position: Int) {
            binding.itemTextView.text = option
            binding.radioButton.isChecked = position == selectedPosition

            binding.radioButton.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()  // Update the RecyclerView to reflect the new selection
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position], position)
    }

    override fun getItemCount() = options.size
}
