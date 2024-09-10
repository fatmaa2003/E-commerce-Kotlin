package com.example.e_commercekotlin.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.DatabaseHelperImpl
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = Repository()
    lateinit var database: DatabaseHelper

    private val _product = MutableLiveData<Resource<ProductResponse>>()
    val data: LiveData<Resource<ProductResponse>> get() = _product

    private val productlistData = MutableLiveData<List<ProductResponse.ProductResponseItem>>()
    val productData: LiveData<List<ProductResponse.ProductResponseItem>>get() = productlistData

    fun fetchProduct(categoryId: String) {
        viewModelScope.launch {
            _product.postValue(Resource.Loading(null))
            try {
                val response = repository.getProductsByCategoryId(categoryId = categoryId)
                response.data?.let { database.insertProducts(it) }
                Log.d(
                    "in view model in product view model",
                    "${response.data?.let { database.insertProducts(it) }}"
                )
                _product.postValue(response)
            } catch (e: Exception) {
                _product.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }

     fun cacheProducts(list: ProductResponse) {
         viewModelScope.launch{
             repository.insertAllProducts(list)
         }
    }

     fun getProducts() {
        viewModelScope.launch {
        productlistData.postValue( repository.getProducts())
        }
    }
}
