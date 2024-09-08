package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = Repository()

    private val _product = MutableLiveData<Resource<List<Product>>>()
    val data: LiveData<Resource<List<Product>>> get() = _product



    fun fetchProduct(categoryId:String) {
        viewModelScope.launch {
            _product.postValue(Resource.Loading)
            try {
                val response = repository.getProductsByCategoryId(categoryId=categoryId)
                _product.postValue(response)
            } catch (e: Exception) {
                _product.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }
}
