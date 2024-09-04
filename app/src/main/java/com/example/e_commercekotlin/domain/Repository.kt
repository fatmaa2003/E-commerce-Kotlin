package com.example.e_commercekotlin.domain

import android.util.Log
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.RetrofitInstance
import com.example.e_commercekotlin.data.RetrofitInstance.api
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class Repository {

    private val apiService = api

    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                Log.d("token", response.body()?.userDetails?.token!!)
                SharedPreferencesHelper.saveToken(response.body()?.userDetails?.token!!)
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Login failed")
            }
        } catch (e: Exception) {
            Log.e("repo_error", e.message.toString())
            Resource.Error(e.message.toString())
        }
    }

    suspend fun signup(signupRequest: SignupRequest): Resource<SignupResponse> {
        return try {
            val response = apiService.signup(signupRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Signup failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    suspend fun getCategories(): Resource<Category> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading(null)
                val response = api.getCategories()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("An error occurred: ${e.message}")
            }
        }
    }

}