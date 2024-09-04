package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.data.User
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = Repository()

    private val _data = MutableLiveData<Resource<List<User>>>()
    val data: LiveData<Resource<List<User>>> get() = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            //n 2ool lel class eli b3dk en di el state
            _data.postValue(Resource.Loading())
            val response = repository.getData()
            _data.postValue(response)
        }
    }
}

