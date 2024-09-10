package com.example.e_commercekotlin.presentation.screens.Product_Details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    private val repository = Repository()

    private val _data = MutableLiveData<Resource<ProductDetailsDto>>()
    val data: LiveData<Resource<ProductDetailsDto>> get() = _data

     fun fetchProductDetails(productId:Long) {
        viewModelScope.launch {
            val response = repository.getProductDetailsById(productId = productId)
            _data.postValue(response)
        }
    }
}
