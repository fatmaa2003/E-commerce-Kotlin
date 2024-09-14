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

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun setAddressList(addressList: List<Address>) {
        this.addressList = addressList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = addressList[position]
        holder.addressName.text = currentItem.addressName
        holder.address.text = currentItem.address
        holder.arrivalEstimate.text = currentItem.arrivalEstimate
        holder.shippingCost.text = currentItem.shippingCost

        holder.itemView.isSelected = (position == selectedPosition)
        holder.addressRadioButton.isChecked = (position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = if (position == selectedPosition) RecyclerView.NO_POSITION else position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
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
