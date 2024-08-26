package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
//    private val repository = Repository()
//
//    private val _data = MutableLiveData<Resource<List<Product>>>()
//    val data: LiveData<Resource<List<Product>>> get() = _data
//
//    init {
//        fetchData()
//    }

//    private fun fetchData() {
//        viewModelScope.launch {
//            //n 2ool lel class eli b3dk en di el state
//            _data.postValue(Resource.Loading())
//            val response = repository.getItems()
//            _data.postValue(response)
//        }
//    }
}

