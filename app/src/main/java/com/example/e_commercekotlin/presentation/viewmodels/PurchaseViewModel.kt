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
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class PurchaseViewModel : ViewModel() {


    private val repository = Repository()

    private val _cartItems = MutableLiveData<Resource<CartItem>>()
    val cartItems: LiveData<Resource<CartItem>> = _cartItems

    private val _purchaseStatus = MutableLiveData<Resource<PurchaseResponse>>()
    val purchaseStatus: LiveData<Resource<PurchaseResponse>> = _purchaseStatus


    init {
        fetchCartItems()
    }

     fun fetchCartItems() {
        viewModelScope.launch {
            _cartItems.postValue(Resource.Loading())
            viewModelScope.launch {
                val response = repository.getCartItems()
                _cartItems.postValue(response)
            }
        }
    }

    fun makePurchase(products: List<AddToCartRequest.Product>) {
        viewModelScope.launch {
            _purchaseStatus.postValue(Resource.Loading())
            viewModelScope.launch {
                val response = repository.makePurchase(products)
                _purchaseStatus.postValue(response)
            }
        }
    }

    private val _deleteProductStatus = MutableLiveData<Resource<Unit>>()
    val deleteProductStatus: LiveData<Resource<Unit>> get() = _deleteProductStatus

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            val response = repository.deleteProductFromCart(productId)
            _deleteProductStatus.postValue(response)
        }
    }

    private val _increaseQuantityState = MutableLiveData<Resource<Unit>>()
    val increaseQuantityState: LiveData<Resource<Unit>> get() = _increaseQuantityState

    private val _decreaseQuantityState = MutableLiveData<Resource<Unit>>()
    val decreaseQuantityState: LiveData<Resource<Unit>> get() = _decreaseQuantityState

    fun increaseProductQuantity(productId: Long) {
        viewModelScope.launch {
            _increaseQuantityState.postValue(Resource.Loading())
            val response = repository.increaseProductQuantity(productId, 1)
            _increaseQuantityState.postValue(response)
        }
    }

    fun decreaseProductQuantity(productId: Long) {
        viewModelScope.launch {
            _decreaseQuantityState.postValue(Resource.Loading())
            val response = repository.decreaseProductQuantity(productId, 1)
            _decreaseQuantityState.postValue(response)
        }
    }
}
