package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.databinding.FragmentPurchaseBinding
import com.example.e_commercekotlin.presentation.adapter.PurchaseAdapter
import com.example.e_commercekotlin.presentation.viewmodel.PurchaseViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchaseFragment : Fragment(R.layout.fragment_purchase) {

    private lateinit var binding: FragmentPurchaseBinding
    val purchaseViewModel: PurchaseViewModel by viewModels()
    private lateinit var purchaseAdapter: PurchaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPurchaseBinding.bind(view)

        val args: PurchaseFragmentArgs by navArgs()
        val cartItem: CartItem = args.cartData

        purchaseAdapter = PurchaseAdapter(emptyList())
        binding.addToCartRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = purchaseAdapter
        }

        purchaseAdapter.updateItems(cartItem.products)

        val subtotal = cartItem.totalCartPrice
        val shipping = 5.0
        val total = subtotal + shipping
        binding.subtotalTv.text = String.format("%.2f", subtotal)
        binding.totalPrice.text = String.format("%.2f", total)
        binding.continueToCheckout.buttonTv.text = "Confirm and pay"

        observePurchaseOrder()
        binding.continueToCheckout.root.setOnClickListener {
            val cartPurchaseData = cartItem.products.map {  AddToCartRequest.Product(productId = it.productId.toLong() , quantity = it.quantity )}
            if (cartPurchaseData.isNotEmpty()) {
                purchaseViewModel.makePurchase(cartPurchaseData)
            } else {
                Toast.makeText(requireContext(), "No products in cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observePurchaseOrder() {
        purchaseViewModel.purchaseStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val invoiceNumber = resource.data?.invoiceNumber
                        Toast.makeText(requireContext(), "Purchase successful! Invoice: $invoiceNumber", Toast.LENGTH_SHORT).show()
                        delay(3000)
                        findNavController().navigate(R.id.action_purchase_to_Feed_fragment)
                    }
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
