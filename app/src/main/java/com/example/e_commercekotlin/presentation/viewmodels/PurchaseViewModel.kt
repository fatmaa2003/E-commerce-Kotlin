package com.example.e_commercekotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.data.model.ProductItem
import com.example.e_commercekotlin.data.model.PurchaseResponse
import com.example.e_commercekotlin.data.remote.ApiService
import kotlinx.coroutines.launch

class PurchaseViewModel(private val apiService: ApiService) : ViewModel() {

    private val _cartItems = MutableLiveData<Resource<CartItem>>()
    val cartItems: LiveData<Resource<CartItem>> = _cartItems

    private val _purchaseStatus = MutableLiveData<Resource<PurchaseResponse>>()
    val purchaseStatus: LiveData<Resource<PurchaseResponse>> = _purchaseStatus

    fun fetchCartItems() {
        viewModelScope.launch {
            _cartItems.postValue(Resource.Loading())
            try {
                val response = apiService.getCartItems()
                if (response.isSuccessful) {
                    _cartItems.postValue(Resource.Success(response.body()!!))
                } else {
                    _cartItems.postValue(Resource.Error("Error loading cart: ${response.code()}"))
                }
            } catch (e: Exception) {
                _cartItems.postValue(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
    fun getCartItems(): List<ProductItem> {
        val cartResponse = _cartItems.value
        return cartResponse?.data?.products ?: emptyList()
    }
    fun makePurchase(products: List<AddToCartRequest.Product>) {
        viewModelScope.launch {
            _purchaseStatus.postValue(Resource.Loading())
            try {
                val response = apiService.makePurchase(AddToCartRequest(products))
                if (response.isSuccessful) {
                    _purchaseStatus.postValue(Resource.Success(response.body()!!))
                } else {
                    _purchaseStatus.postValue(Resource.Error("Purchase failed with status code ${response.code()}"))
                }
            } catch (e: Exception) {
                _purchaseStatus.postValue(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}
