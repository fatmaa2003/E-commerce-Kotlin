package com.example.e_commercekotlin

import com.example.e_commercekotlin.data.model.Product

interface DatabaseHelper {
    suspend fun getProducts(): List<Product>
    suspend fun insertProducts(products: List<Product>)
}
