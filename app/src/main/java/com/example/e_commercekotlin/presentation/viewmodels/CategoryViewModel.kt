package com.example.e_commercekotlin.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val repository = Repository()

    init {
        fetchCategories()
    }

    private val _data = MutableLiveData<Resource<Category>>()
    val data: LiveData<Resource<Category>> get() = _data


    private fun fetchCategories() {
        viewModelScope.launch {
            val response = repository.getCategories()
            _data.postValue(response)
        }
    }

//
//    fun cacheCategory(list: Category) {
//        viewModelScope.launch {
//            repository.insertAllCategories(list)
//        }
//    }

}