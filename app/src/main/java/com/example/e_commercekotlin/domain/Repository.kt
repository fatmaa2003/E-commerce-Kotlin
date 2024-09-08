package com.example.e_commercekotlin.domain

import android.util.Log
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.RetrofitInstance
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.data.model.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val apiService = RetrofitInstance.api

    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
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

    suspend fun getProductsByCategoryId(categoryId:String): Resource<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProductsByCategoryId(categoryId = categoryId)
                Log.d("in repo"," the response in repo $response")
                if (response.isSuccessful) {
                    Resource.Success(response.body() ?: emptyList())
                } else {
                    Resource.Error("Product retrieval failed")
                }
            } catch (e: Exception) {
                Resource.Error("An error occurred: ${e.message}")
            }
        }
    }
}
