package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.FreshCollection
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionViewModel : ViewModel() {
    private val repository = Repository()
    val freshCollections = MutableLiveData<Resource<FreshCollection>>()

    init {
        getFreshCollections()
    }

    private fun getFreshCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            freshCollections.postValue(Resource.Loading())
            try {
                val response = repository.getFreshCollection()
                freshCollections.postValue(response)
            } catch (e: Exception) {
                freshCollections.postValue(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
