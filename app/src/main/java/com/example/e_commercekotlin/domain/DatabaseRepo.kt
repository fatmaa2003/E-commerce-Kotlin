package com.example.e_commercekotlin.domain

import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.model.ProductResponse

class DatabaseRepo  {

    lateinit var databaseHelper: DatabaseHelper


    suspend fun getProducts() : List<ProductResponse.ProductResponseItem>{
        return databaseHelper.getProducts()
    }

    suspend fun insertProducts(products : List<ProductResponse.ProductResponseItem>){
        databaseHelper.insertProducts(products)
    }
}