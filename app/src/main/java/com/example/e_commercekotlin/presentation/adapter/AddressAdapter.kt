package com.example.e_commercekotlin.presentation.adapter

import Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R


class AddressAdapter(
    private var addressList: List<Address> = listOf()
) : RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {

    private var selectedPosition: Int = if (addressList.isNotEmpty()) 0 else RecyclerView.NO_POSITION
    private var isProfileAdapter = false

    fun setAddressList(addressList: List<Address>) {
        this.addressList = addressList
        selectedPosition = if (addressList.isNotEmpty()) 0 else RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun setIsProfileAdapter(isProfileAdapter: Boolean) {
        this.isProfileAdapter = isProfileAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = addressList[holder.adapterPosition]

        holder.addressName.text = currentItem.addressName
        holder.address.text = currentItem.address
        holder.arrivalEstimate.text = currentItem.arrivalEstimate
        holder.shippingCost.text = currentItem.shippingCost

        holder.itemView.isSelected = (holder.adapterPosition == selectedPosition)
        holder.addressRadioButton.isChecked = (holder.adapterPosition == selectedPosition)

        holder.itemView.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos != selectedPosition) {  // Prevent deselection
                val previousSelectedPosition = selectedPosition
                selectedPosition = currentPos
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }

        holder.addressRadioButton.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION && currentPos != selectedPosition) {  // Prevent deselection
                val previousSelectedPosition = selectedPosition
                selectedPosition = currentPos
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }
        if (isProfileAdapter) {
            holder.itemView.setOnClickListener(null)
            holder.addressRadioButton.setOnClickListener(null)
            holder.addressRadioButton.visibility = View.GONE
        }else {
            holder.addressRadioButton.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressName: TextView = itemView.findViewById(R.id.address_name_tv)
        val address: TextView = itemView.findViewById(R.id.item_address_text)
        val arrivalEstimate: TextView = itemView.findViewById(R.id.arrival_estimate_tv)
        val shippingCost: TextView = itemView.findViewById(R.id.shipping_cost_tv)
        val addressRadioButton: RadioButton = itemView.findViewById(R.id.address_radio_button)
    }
}
