package com.example.e_commercekotlin

import com.example.e_commercekotlin.data.model.ProductResponse


interface DatabaseHelper {
    suspend fun getProducts(): List<ProductResponse.ProductResponseItem>
    suspend fun insertProducts(products: List<ProductResponse.ProductResponseItem>)
}
