import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.databinding.DressesVerticalBinding

class DressesAdapter() : RecyclerView.Adapter<DressesAdapter.ProductViewHolder>() {

    private var dressesList: List<ProductResponse.ProductResponseItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = DressesVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = dressesList[position]
        Log.d("TAG","prouduct in adapter$product")
        holder.bind(product)
    }

    override fun getItemCount() = dressesList.size

    inner class ProductViewHolder(private val binding: DressesVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponse.ProductResponseItem) {
            // Assuming you use a library like Glide or Picasso to load images
            Glide.with(binding.imageview.context)
                .load(product.imageUrl) // Replace with your image URL field
                .into(binding.imageview)
        }
    }


    fun setDressesList(dressesList : List<ProductResponse.ProductResponseItem>) {
        this.dressesList = dressesList
        notifyDataSetChanged()
    }
}
