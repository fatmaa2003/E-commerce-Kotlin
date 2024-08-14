package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.model.SignupResponse
import com.example.e_commercekotlin.domain.Repository
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: Repository) : ViewModel() {

    private val _signupState = MutableLiveData<Resource<SignupResponse>>(Resource.Loading)
    val signupState: LiveData<Resource<SignupResponse>> get() = _signupState

    fun signup(signupRequest: SignupRequest) {
        viewModelScope.launch {
            _signupState.postValue(Resource.Loading)
            val result = repository.signup(signupRequest = signupRequest)
            _signupState.postValue(result)
        }
    }
}
