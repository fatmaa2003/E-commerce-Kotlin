package com.example.e_commercekotlin.domain

import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.RetrofitInstance
import com.example.e_commercekotlin.data.RetrofitInstance.api
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val apiService = RetrofitInstance.api

    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Login failed: Empty response body")
            } else {
                Resource.Error("Login failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during login")
        }
    }

    suspend fun signup(signupRequest: SignupRequest): Resource<SignupResponse> {
        return try {
            val response = apiService.signup(signupRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it, )
                } ?: Resource.Error("Signup failed: Empty response body")
            } else {
                Resource.Error("Signup failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred during signup")
        }
    }

    suspend fun getData(): Resource<List<User>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getData()
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("An error occurred: ${e.message}", null)
            }
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
