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

    private val _loginState = MutableLiveData<Resource<UserRole>>()
    val loginState: LiveData<Resource<UserRole>> get() = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.postValue(Resource.Loading())  // Indicate loading state

            val result = repository.login(username, password)

            when (result) {
                is Resource.Success -> {
                    val userRole = when (result.data?.role) {
                        "ADMIN" -> UserRole.ADMIN
                        "USER" -> UserRole.USER
                        else -> UserRole.USER
                    }
                    _loginState.postValue(Resource.Success(userRole, result.message))
                }
                is Resource.Error -> {
                    _loginState.postValue(Resource.Error(result.message ?: "Unknown error"))
                }
                else -> {
                    _loginState.postValue(Resource.Error("Unknown error"))
                }
            }
        }
    }
}
