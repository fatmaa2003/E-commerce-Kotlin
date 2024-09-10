package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.AddToCartResponse
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class AddToCartViewModel(private val repository: Repository): ViewModel() {
    private val _addToCartState = MutableLiveData<Resource<AddToCartResponse>>(Resource.Loading())
    val addToCartState: LiveData<Resource<AddToCartResponse>> get() = _addToCartState

    fun addToCart(addToCartRequest: AddToCartRequest)
    {
        viewModelScope.launch {
            _addToCartState.postValue(Resource.Loading())
            val result = repository.addToCart(addToCartRequest = addToCartRequest)
            _addToCartState.postValue(result)
        }
    }
}