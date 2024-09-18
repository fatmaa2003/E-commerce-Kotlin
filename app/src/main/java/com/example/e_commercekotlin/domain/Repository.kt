package com.example.e_commercekotlin.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.RetrofitInstance.api
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.data.model.AllProdcutsDto
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.CategoryDetails
import com.example.e_commercekotlin.data.model.DressDetailsDto
import com.example.e_commercekotlin.data.model.FreshCollection
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.data.model.PurchaseResponse
import com.example.e_commercekotlin.data.model.SignupResponse
import com.example.e_commercekotlin.data.model.StoreDetailsDto
import com.example.e_commercekotlin.data.model.Stores
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val apiService = api

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

    suspend fun getProducts(): Resource<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading(null)
                val response = api.getAllProducts()
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

    suspend fun getCategoryById(categoryId: String) :Resource<DressDetailsDto>{

        return withContext(Dispatchers.IO){
            try {
                Resource.Loading(null)
                Log.d("categoryId",categoryId.toString())
                val response= api.getCategoryById(categoryId = categoryId)
                if (response.isSuccessful){
                    Resource.Success(response.body()!!)
                }

                else{
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }
            }

            catch (e: Exception) {
                Resource.Error("An error occurred: ${e.message}")
            }
        }
    }

    suspend fun getStoreDetailsById(storeId: String) :Resource<StoreDetailsDto>{

        return withContext(Dispatchers.IO){
            try {
                Resource.Loading(null)
                val response= api.getStoresById(storeId = storeId)
                if (response.isSuccessful){
                    Resource.Success(response.body()!!)
                }

                else{
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }
            }

            catch (e: Exception) {
                Resource.Error("An error occurred: ${e.message}")
            }
        }
    }


    suspend fun getStores(): Resource<Stores> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading(null)
                val response= api.getStores()
                if (response.isSuccessful){
                    Log.e("TAG123", "getStores: " )
                    Resource.Success(response.body()!!)
                }

                else{
                    Log.e("TAG123", "getStores: " +response.message().toString())
                    Resource.Error("Error: ${response.code()} ${response.message()}")
                }

            }

            catch (e: Exception) {
                Log.e("TAG123", "getStores: " + e.message )
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
            val response = apiService.addToCart(AddToCartRequest(listOf(AddToCartRequest.Product(productId, quantity)) ))
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error adding item to cart")
            }
        } catch (e: Exception) {
            Resource.Error("Network error")
        }
    }

    suspend fun makePurchase(products: List<AddToCartRequest.Product>): Resource<PurchaseResponse> {
        return try {
            val purchaseRequest = AddToCartRequest(products)
            Resource.Loading(null)
            val response = apiService.makePurchase(purchaseRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error fetching cart size")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getCartItems(): Resource<CartItem> {
        return try {
            Resource.Loading(null)
            val response = apiService.getCartItems()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error fetching cart size")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun deleteProductFromCart(productId: Long): Resource<Unit> {
        return try {
            val response = apiService.deleteProductFromCart(productId)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Failed to delete product")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    suspend fun increaseProductQuantity(productId: Long,quantity: Int): Resource<Unit> {
        return try {
            val response = apiService.increaseCartQuantity(productId, quantity = 1)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun decreaseProductQuantity(productId: Long,quantity: Int): Resource<Unit> {
        return try {
            val response = apiService.decreaseCartQuantity(productId, quantity = 1)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getFreshCollection(): Resource<FreshCollection> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Loading(null)
                val response = apiService.getFreshCollections()
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