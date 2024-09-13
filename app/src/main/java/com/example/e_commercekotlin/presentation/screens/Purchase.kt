package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.databinding.FragmentPurchaseBinding
import com.example.e_commercekotlin.presentation.adapter.PurchaseAdapter
import com.example.e_commercekotlin.presentation.viewmodel.PurchaseViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel

class PurchaseFragment : Fragment(R.layout.fragment_purchase) {

    private lateinit var binding: FragmentPurchaseBinding
//    private lateinit var viewModel: PurchaseViewModel
    private lateinit var purchaseAdapter: PurchaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPurchaseBinding.bind(view)
        val PurchaseViewModel: PurchaseViewModel by viewModels()

        purchaseAdapter = PurchaseAdapter(emptyList())
        binding.addToCartRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = purchaseAdapter
        }
        PurchaseViewModel.cartItems.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cartResponse = resource.data
                    val cartItems = cartResponse?.products ?: emptyList()
                    purchaseAdapter.updateItems(cartItems)

                    val subtotal = cartResponse?.totalCartPrice ?: 0.0
                    val shipping = 5.0
                    val total = subtotal + shipping

                    binding.totalPrice.text = "Subtotal: $${String.format("%.2f", subtotal)}\nTotal: $${String.format("%.2f", total)}"
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Failed to load cart: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                }
            }
        }

        binding.confirmandpay.setOnClickListener {
            val cartItems = PurchaseViewModel.getCartItems()
            if (cartItems.isNotEmpty()) {
                val productsFromCart :  List<AddToCartRequest.Product> = listOf()
                PurchaseViewModel.makePurchase(productsFromCart)
            } else {
                Toast.makeText(requireContext(), "No products in cart", Toast.LENGTH_SHORT).show()
            }
        }


        PurchaseViewModel.purchaseStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val invoiceNumber = resource.data?.invoiceNumber
                    Toast.makeText(requireContext(), "Purchase successful! Invoice: $invoiceNumber", Toast.LENGTH_SHORT).show()
                    binding.confirmandpay.postDelayed({
                        findNavController().navigate(R.id.action_purchase_to_Feed_fragment)
                    }, 3000)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Purchase failed: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }
}
