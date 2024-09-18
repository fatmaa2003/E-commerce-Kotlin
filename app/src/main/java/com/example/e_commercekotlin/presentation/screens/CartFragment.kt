package com.example.e_commercekotlin.presentation.screens

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.databinding.FragmentCartBinding
import com.example.e_commercekotlin.presentation.viewmodel.PurchaseViewModel
import com.example.e_commercekotlin.presentation.viewmodels.SharedCartViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: PurchaseViewModel by viewModels()
    private var cartData: CartItem? = null
    private var adapterPosition = -1
    private val sharedCartViewModel: SharedCartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueToCheckout.buttonTv.text = "Continue to checkout"
        activity?.setBottomNavVisibility(false)
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

        binding.continueToCheckout.root.setOnClickListener {
            if (cartData != null && cartData!!.cartSize > 0) { // Ensure cartData is not null and not empty
                val action = CartFragmentDirections.actionCartToShippingAddressFragment2(cartData!!)
                findNavController().navigate(action)
            } else {
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

                    cartData?.products?.getOrNull(adapterPosition)?.apply {
                        quantity += 1
                        productPrice=itemTotalPrice*quantity
                    }
                    cartAdapter.notifyItemChanged(adapterPosition)
                    updateTotalCartPrice()
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

                    cartData?.products?.get(adapterPosition)?.apply {
                        quantity -= 1
                        productPrice=productPrice-itemTotalPrice
                    }
                    cartAdapter.notifyItemChanged(adapterPosition)
                    updateTotalCartPrice()
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

                    viewModel.fetchCartItems()
                    updateTotalCartPrice()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun updateTotalCartPrice() {
        var totalCartPrice = 0.0
        cartData?.products?.forEach {
            totalCartPrice += it.productPrice
        }
        binding.totalPriceTextView.text = "Total: $$totalCartPrice"
        sharedCartViewModel.updateSubtotal(totalCartPrice)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
