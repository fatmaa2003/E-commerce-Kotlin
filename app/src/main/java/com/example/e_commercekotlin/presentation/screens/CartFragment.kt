package com.example.e_commercekotlin.presentation.screens

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.databinding.FragmentCartBinding
import com.example.e_commercekotlin.presentation.viewmodel.PurchaseViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: PurchaseViewModel by viewModels()
    private lateinit var cartData : CartItem

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
            val action = CartFragmentDirections.actionCartToShippingAddressFragment2(cartData)
            findNavController().navigate(action)
        }
    }


    private fun fetchCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner) { resources ->

            when (resources) {
                is Resource.Loading -> {
                    // handle progress bar visibility
                }
                is Resource.Success -> {
                    val cartItem = resources.data
                    cartData = resources.data!!
                    if (cartItem != null && cartItem.products.isNotEmpty()) {
                        cartAdapter.setProductItems(cartItem.products)

                        val totalCartPrice = cartItem.totalCartPrice
                        binding.totalPriceTextView.text = "Total: $$totalCartPrice"
                    } else {
                        Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
