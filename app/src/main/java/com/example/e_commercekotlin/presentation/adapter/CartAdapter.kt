import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.databinding.ItemCartBinding
import com.example.e_commercekotlin.data.model.ProductItem
import com.squareup.picasso.Picasso
class CartAdapter(
    private var productItems: List<ProductItem>,
    private val onDeleteClick: (ProductItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val productItem = productItems[position]
        holder.binding.apply {
            Glide.with(productName.context).load(productItem.productMainImage).into(productImage)

            productName.text = productItem.productName
            productDetails.text = "Quantity: ${productItem.quantity}"
            productPrice.text = "$${productItem.productPrice}"

            deleteButton.setOnClickListener {
                onDeleteClick(productItem)
            }
        }
    }

    override fun getItemCount(): Int = productItems.size

    fun setProductItems(productItems: List<ProductItem>) {
        this.productItems = productItems
        notifyDataSetChanged()
    }
}
