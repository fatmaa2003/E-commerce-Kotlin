    import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.e_commercekotlin.R
    import com.example.e_commercekotlin.data.RetrofitInstance
import com.example.e_commercekotlin.databinding.FragmentCartBinding
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(emptyList())

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter

        fetchCartItems()

        binding.continueToCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cart_to_purchase)
        }
    }


    private fun fetchCartItems() {
        lifecycleScope.launch {
            try {
                val cartResponse = RetrofitInstance.api.getCartItems()
                if (cartResponse.isSuccessful) {
                    val cartItem = cartResponse.body()
                    if (cartItem != null && cartItem.products.isNotEmpty()) {
                        cartAdapter.setProductItems(cartItem.products)

                        val totalCartPrice = cartItem.totalCartPrice
                        binding.totalPriceTextView.text = "Total: $$totalCartPrice"
                    } else {
                        Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
