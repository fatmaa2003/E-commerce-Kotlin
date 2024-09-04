package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.data.model.UserRole
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<LoginResponse>>(Resource.Loading())
    val loginState: LiveData<Resource<LoginResponse>> get() = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.postValue(Resource.Loading())
            val result = repository.login(username,password)
            _loginState.postValue(result)
        }
    }
}
