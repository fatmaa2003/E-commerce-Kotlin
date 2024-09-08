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

    private val _data = MutableLiveData<Resource<List<ProductDetailsDto>>>()
    val data: LiveData<Resource<List<ProductDetailsDto>>> get() = _data

    fun fetchData(productId : String) {
        viewModelScope.launch {
            _data.postValue(Resource.Loading)
            val response = repository.getProductDetailsDto(productId = productId)
            _data.postValue(response)
        }
    }
}
