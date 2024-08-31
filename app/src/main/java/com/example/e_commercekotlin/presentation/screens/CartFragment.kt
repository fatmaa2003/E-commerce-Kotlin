import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.databinding.FragmentCartBinding
import com.example.e_commercekotlin.databinding.MainButtonBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartItems: List<CartItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        cartItems = listOf(
            CartItem(R.drawable.treka, "Bershka Mom Jeans", "S - 26 | Blue | x1", "$34"),
            CartItem(R.drawable.treka, "Bershka Mom Jeans", "M - 28 | Black | x1", "$36")
        )

        cartAdapter = CartAdapter(cartItems)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter


        val buttonBinding = MainButtonBinding.bind(binding.continueToCheckout.root)
        buttonBinding.buttonTv.text = "Proceed to Checkout"

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

