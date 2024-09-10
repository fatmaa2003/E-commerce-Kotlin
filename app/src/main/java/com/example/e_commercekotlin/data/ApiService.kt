package com.example.e_commercekotlin.data

import androidx.room.Query
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.AddToCartResponse
import com.example.e_commercekotlin.data.model.Cart
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.data.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    suspend fun getData(): Response<List<User>>

    @POST("signup")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @GET("categories")
    suspend fun getCategories(): Response<Category>

    @GET("cats?limit=8")
    suspend fun getCollections(): List<Collection>

    @GET("products/by_category/{categoryId}")
    suspend fun getProductsByCategoryId(@Path("categoryId") categoryId: String) : Response<ProductResponse>
//
//    @GET("products")
//    suspend fun getStoreImages(): List<Product>
    @POST("cart/add")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<AddToCartResponse>

    @GET("cart/view")
    suspend fun getCart(): Response<Cart>
}
