package com.example.e_commercekotlin.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.CategoryDetails
import com.example.e_commercekotlin.data.model.Stores
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class StoresViewModel : ViewModel() {

    private val repository = Repository()

    private val _category_details = MutableLiveData<Resource<CategoryDetails>>()
    val data: LiveData<Resource<CategoryDetails>> get() = _category_details

    fun fetchCategoryDetails(categoryid:String) {
        viewModelScope.launch {
            _category_details.postValue(Resource.Loading(null))
            try {
                val response = repository.getCategoryById(categoryid=categoryid)
                _category_details.postValue(response)
            } catch (e: Exception) {
                _category_details.postValue(Resource.Error("An error occurred: ${e.message}"))
            }
        }
    }
}
