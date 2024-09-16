package com.example.e_commercekotlin.presentation.viewmodels

import android.util.Log
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

    private val _stores = MutableLiveData<Resource<Stores>>()
    val stores: LiveData<Resource<Stores>> get() = _stores

    init {
        fetchStores()
    }

    private fun fetchStores() {
        viewModelScope.launch {
            try {
                val response = repository.getStores()

                if (response is Resource.Success) {
                    Log.d("StoresViewModel", "API Response Data: ${response.data}")
                } else {
                    Log.d("StoresViewModel", "API Response: $response")
                }
                _stores.postValue(response)
            } catch (e: Exception) {
                _stores.postValue(Resource.Error("Failed to load stores"))
                Log.e("StoresViewModel", "Error fetching stores: ${e.message}")
            }
        }
    }
}
