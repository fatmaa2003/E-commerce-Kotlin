package com.example.e_commercekotlin.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.DatabaseHelperImpl
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.AllProductModel
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = Repository()
    lateinit var database: DatabaseHelper

    private val _product = MutableLiveData<Resource<ProductResponse>>()
    val data: LiveData<Resource<ProductResponse>> get() = _product

    init {
        fetchProduct()
    }

    fun fetchProduct(categoryId:String? = "16") {
        viewModelScope.launch {
            _product.postValue(Resource.Loading(null))
            try {
                val response = repository.getProductsByCategoryId(categoryId=categoryId.orEmpty())
                _product.postValue(response)
                response.data?.let { database.insertProducts(it) }
                Log.d(
                    "in view model in product view model",
                    "${response.data?.let { database.insertProducts(it) }}"
                )
            } catch (e: Exception) {
                _product.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }

//     fun cacheProducts(list: ProductResponse) {
//         viewModelScope.launch{
//             repository.insertAllProducts(list)
//         }
//    }
//
//     fun getProducts() {
//        viewModelScope.launch {
//        productlistData.postValue( repository.getProducts())
//        }
//    }

    private val _allProduct = MutableLiveData<Resource<AllProductModel>>()
    val allProduct: LiveData<Resource<AllProductModel>> get() = _allProduct

    fun getAllProduct() {
        viewModelScope.launch {
            _allProduct.postValue(Resource.Loading(null))
            try {
                val response = repository.getProducts()
                _allProduct.postValue(response)
            } catch (e: Exception) {
                _allProduct.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }
}
