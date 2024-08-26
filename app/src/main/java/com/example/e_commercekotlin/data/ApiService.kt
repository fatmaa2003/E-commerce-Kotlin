package com.example.e_commercekotlin.data

import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.data.model.FrequentlyVisitedItems
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.data.model.SignupResponse
import com.example.e_commercekotlin.data.model.StoreImages
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("signup")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("products")
    suspend fun getItems(): List<Product>
//    @GET("products")
//    suspend fun getItems(): Response<List<Product>>

    @GET("cats?limit=8")
    suspend fun getCollections(): List<Collection>

    @GET("products")
    suspend fun getFreqVisitedItems(): List<FrequentlyVisitedItems>

    @GET("products")
    suspend fun getStoreImages(): List<StoreImages>
}