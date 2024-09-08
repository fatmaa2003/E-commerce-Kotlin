package com.example.e_commercekotlin.data

import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.model.Product

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getProducts(): List<Product> = appDatabase.ProductDao().getAllProducts()
    override suspend fun insertProducts(products: List<Product>) = appDatabase.ProductDao().insertAll(products)
}
