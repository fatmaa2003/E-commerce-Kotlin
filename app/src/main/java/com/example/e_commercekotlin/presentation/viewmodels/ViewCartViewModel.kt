package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Cart
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ViewCartViewModel: ViewModel() {
    private val repository= Repository()

    init {
        fetchCart()
    }

    private val _data = MutableLiveData<Resource<Cart>>()
    val data: LiveData<Resource<Cart>> get() = _data


    private fun fetchCart() {
        viewModelScope.launch {
            val response = repository.getCart()
            repository.insertCart(cart = response.data!!)
            _data.postValue(response)
        }
    }
}