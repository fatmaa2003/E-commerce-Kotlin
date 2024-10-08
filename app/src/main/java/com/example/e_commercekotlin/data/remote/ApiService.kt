package com.example.e_commercekotlin.data.remote

import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.data.User
import com.example.e_commercekotlin.data.model.AddToCartRequest
import com.example.e_commercekotlin.data.model.CartItem
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    @GET("cart/view")
    suspend fun getCartSize():Response<CartItem>

    @POST("cart/add")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<Unit>

    @GET("products")
    suspend fun getAllProducts () : Response<List<Product>>

    @GET("products/{productId}")
    suspend fun getProductDetailsById(@Path("productId") productId : Long) : Response<ProductDetailsDto>

    @GET("categories/{categoryid}")
    suspend fun getCategoryById(@Path("categoryid") categoryId:String) : Response<DressDetailsDto>

    @GET("categories/{categoryid}")
    suspend fun getStoresById(@Path("categoryid") storeId:String) : Response<StoreDetailsDto>

    @GET("categories/stores")
    suspend fun getStores():Response<Stores>

    @POST("cart/purchase")
    suspend fun makePurchase(@Body purchaseRequest:AddToCartRequest): Response<PurchaseResponse>

    @DELETE("cart/remove/{productId}")
    suspend fun deleteProductFromCart(@Path("productId") productId: Long): Response<Unit>


    @POST("cart/increase")
    suspend fun increaseCartQuantity(
        @retrofit2.http.Query("productId") productId: Long,
        @retrofit2.http.Query("quantity") quantity: Int
    ): Response<Unit>

    @POST("cart/decrease")
    suspend fun decreaseCartQuantity(
        @retrofit2.http.Query("productId") productId: Long,
        @retrofit2.http.Query("quantity") quantity: Int
    ): Response<Unit>


    @GET("products/fresh_collections")
    suspend fun getFreshCollections(): Response<FreshCollection>

}