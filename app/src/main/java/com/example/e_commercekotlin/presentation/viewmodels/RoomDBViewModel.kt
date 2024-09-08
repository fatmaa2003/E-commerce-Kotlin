package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.AppApiHelper
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.domain.DatabaseRepo
import kotlinx.coroutines.launch

class RoomDBViewModel(private val appApiHelper: AppApiHelper, private val dbRepo: DatabaseRepo) :
    ViewModel() {

         var productsFromDb : MutableLiveData<List<Product>> = MutableLiveData()
    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                 productsFromDb.value = dbRepo.getProducts()
                // here you have your CoursesFromDb
            } catch (e: Exception) {
                // handler error
            }
        }
    }
}