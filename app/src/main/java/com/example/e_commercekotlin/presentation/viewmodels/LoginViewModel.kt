package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.data.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<LoginResponse>>(Resource.Loading)
    val loginState: LiveData<Resource<LoginResponse>> get() = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.postValue(Resource.Loading)
            val result = repository.login(username,password)
            _loginState.postValue(result)

//            when (val result = repository.login(username, password)) {
//                is Resource.Success -> {
//                    result.
//                    val userRole = if (result.data.role == "ADMIN") UserRole.ADMIN else UserRole.USER
//                    _loginState.value = Resource.Success(userRole)
//                    //check eza kan admin wla user
//                }
//                is Resource.Error -> _loginState.value = Resource.Error(result.message)
//                else -> _loginState.value = Resource.Error("Unknown error")
//            }
        }
    }
}
