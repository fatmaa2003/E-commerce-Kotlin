import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Cart
import com.example.e_commercekotlin.databinding.ItemCartBinding
import com.squareup.picasso.Picasso

class CartAdapter(private var cart: Cart) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // Extract the list of products from the Cart object
    private var cartItems: List<Cart.Product?> = cart.products ?: emptyList()

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.binding.apply {
            // Use Picasso to load the image from URL or set a placeholder
            Picasso.get()
                .load(product?.productMainImage) // URL or path to the image
                .placeholder(R.drawable.ic_launcher_background) // Optional placeholder image
                .error(R.drawable.ic_launcher_background) // Optional error image
                .into(productImage)

            productName.text = product?.productName ?: "Unknown Product"
            productDetails.text = "Details not available" // You might need to adjust this
            productPrice.text = product?.productPrice?.toString() ?: "Price not available"
        }
    }

    fun updateCart(newCart: Cart) {
        cart = newCart
        cartItems = newCart.products ?: emptyList()
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun getItemCount(): Int = cartItems.size
}
