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
    private var cartData: CartItem? = null
    private var adapterPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(
            emptyList(),
            onDeleteClick = { productItem  ->
                viewModel.deleteProduct(productItem.productId)
            },
            onIncreaseClick = { productItem , position ->
                adapterPosition = position
                viewModel.increaseProductQuantity(productItem.productId)
            },
            onDecreaseClick = { productItem , position ->
                adapterPosition = position
                viewModel.decreaseProductQuantity(productItem.productId)
            }
        )

        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter

        fetchCartItems()

        binding.continueToCheckout.setOnClickListener {
            cartData?.let {
                val action = CartFragmentDirections.actionCartToPurchase(it)
                findNavController().navigate(action)
            } ?: run {
                Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
            }
        }

        observeDeleteProductStatus()
        observeOIncreaseQuantityStatus()
        observeDecreaseQuantityStatus()
    }

    private fun fetchCartItems() {
        viewModel.cartItems.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val cartItem = resources.data
                    cartData = cartItem

                    if (cartItem != null) {
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

    private fun observeOIncreaseQuantityStatus() {
        viewModel.increaseQuantityState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Cart updated", Toast.LENGTH_SHORT).show()
                    cartData?.products?.get(adapterPosition)?.apply {
                        quantity += 1
                    }
                    cartAdapter.notifyItemChanged(adapterPosition)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeDecreaseQuantityStatus() {
        viewModel.decreaseQuantityState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Cart updated", Toast.LENGTH_SHORT).show()
                    cartData?.products?.get(adapterPosition)?.apply {
                        quantity -= 1
                    }
                    cartAdapter.notifyItemChanged(adapterPosition)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeDeleteProductStatus() {
        viewModel.deleteProductStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Product deleted", Toast.LENGTH_SHORT).show()
                    viewModel.fetchCartItems()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
