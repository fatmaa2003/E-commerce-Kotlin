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

    init {
        fetchStores()
    }

    private val _stores = MutableLiveData<Resource<Stores>>()
    val data: LiveData<Resource<Stores>> get() = _stores

    private fun fetchStores() {
        viewModelScope.launch {
            val response=repository.getStores()
            Log.e("TAG123", "fetchStores: " + response)
            _stores.postValue(response)
        }
    }
}