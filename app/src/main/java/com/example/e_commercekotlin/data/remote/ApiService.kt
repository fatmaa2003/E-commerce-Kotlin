package com.example.e_commercekotlin.data.remote

import com.example.e_commercekotlin.data.model.AllProductModel
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.CategoryDetails
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.data.model.LoginRequest
import com.example.e_commercekotlin.data.model.LoginResponse
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.data.model.SignupResponse
import com.example.e_commercekotlin.data.model.StoreDetailsDto
import com.example.e_commercekotlin.data.model.Stores
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
    @GET("cart/view")
    suspend fun getCartItems():Response<CartItem>

    @POST("cart/add")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<Unit>

    @GET("products")
    suspend fun getAllProducts () : Response<AllProductModel>

      @GET("products/{productId}")
      suspend fun getProductDetailsById(@Path("productId") productId : Long) : Response<ProductDetailsDto>


    @GET("categories/{categoryid}")
    suspend fun getCategoryById(@Path("categoryid") categoryid:String) : Response<CategoryDetails>

    @GET("categories/{categoryid}")
    suspend fun getStoresById(@Path("categoryid") storeId:String) : Response<StoreDetailsDto>

    @GET("categories/stores")
    suspend fun getStores():Response<Stores>

}
