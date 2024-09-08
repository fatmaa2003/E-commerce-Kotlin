package com.example.e_commercekotlin.data

import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.model.ProductResponse

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getProducts(): List<ProductResponse.ProductResponseItem> = appDatabase.ProductDao().getAllProducts()
    override suspend fun insertProducts(products: List<ProductResponse.ProductResponseItem>) = appDatabase.ProductDao().insertAll(products)
}
