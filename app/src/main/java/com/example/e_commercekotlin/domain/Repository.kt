package com.example.e_commercekotlin.domain

import android.util.Log
import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.DatabaseHelperImpl
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.RetrofitInstance.api
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.data.model.SignupResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class Repository {

    private val apiService = api
//     lateinit var databaseHelper: DatabaseHelper = DatabaseHelperImpl()

    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            Log.d("in login in repo ","${response}")
            if (response.isSuccessful) {
                Log.d("token", response.body()?.userDetails?.token!!)
                SharedPreferencesHelper.saveToken(response.body()?.userDetails?.token!!)
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Login failed: ${response.message()}")
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

    suspend fun getProductsByCategoryId(categoryId : String): Resource<ProductResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading(null)
                val response = api.getProductsByCategoryId(categoryId = categoryId)
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


//    suspend fun insertAllProducts(list : List<ProductResponse.ProductResponseItem>) {
//        databaseHelper.insertProducts(list)
//    }
//    suspend fun getProducts() : List<ProductResponse.ProductResponseItem>
//    {
//      return  databaseHelper.getProducts()
//
//    }
//
//    suspend fun insertAllCategories(list : Category) {
//        databaseHelper.insertCategory(list)
//    }

    suspend fun getProductDetailsById(productId:Long) : Resource<ProductDetailsDto> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Loading(null)
                val response = api.getProductDetailsById(productId)
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


    suspend fun addToCart(productId: Long, quantity: Int): Resource<Unit> {
        return try {
            val response = apiService.addToCart(AddToCartRequest(productId, quantity))
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error adding item to cart")
            }
        } catch (e: Exception) {
            Resource.Error("Network error")
        }
    }
}
