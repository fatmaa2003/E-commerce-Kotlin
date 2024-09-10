package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Stores
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class StoresViewModel : ViewModel() {

    private val repository = Repository()

    init {
        fetchStores()
    }

    private val _stores = MutableLiveData<Resource<Stores>>()
    val data: LiveData<Resource<Stores>> get() = _stores

    fun fetchStores() {
        viewModelScope.launch {
            _stores.postValue(Resource.Loading(null))
            try {
                val response = repository.getStores()
                _stores.postValue(response)
            } catch (e: Exception) {
                _stores.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }
}