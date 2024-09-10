import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Cart
import com.example.e_commercekotlin.databinding.FragmentCartBinding
import com.example.e_commercekotlin.databinding.MainButtonBinding
import com.example.e_commercekotlin.presentation.viewmodels.ViewCartViewModel


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
//    private lateinit var cartItems: List<CartItem>
    private val viewCartViewModel: ViewCartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
//
//        cartItems = listOf(
//            CartItem(R.drawable.treka, "Bershka Mom Jeans", "S - 26 | Blue | x1", "$34"),
//            CartItem(R.drawable.treka, "Bershka Mom Jeans", "M - 28 | Black | x1", "$36")
//        )

//        cartAdapter = CartAdapter(cartItems)
        cartAdapter = CartAdapter(Cart(message = null, products = emptyList(), statusCode = null, totalCartPrice = null))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter


        val buttonBinding = MainButtonBinding.bind(binding.continueToCheckout.root)
        buttonBinding.buttonTv.text = "Proceed to Checkout"

        observeCart()

        return view
    }

    private fun observeCart() {
        viewCartViewModel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { newCart ->
                        cartAdapter.updateCart(newCart) // Update the adapter with new Cart data
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error loading cart", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

