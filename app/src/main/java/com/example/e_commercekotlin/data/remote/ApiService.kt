package com.example.e_commercekotlin.data.remote

import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.ProductDetailsDto
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


    @GET("products/by_category/{categoryId}")
    suspend fun getProductsByCategoryId(@Path("categoryId") categoryId: String) : Response<ProductResponse>
//
      @GET("products/{productId}")
      suspend fun getProductDetailsById(@Path("productId") productId : Long) : Response<ProductDetailsDto>
    // @GET("")
    //suspend fun
}
