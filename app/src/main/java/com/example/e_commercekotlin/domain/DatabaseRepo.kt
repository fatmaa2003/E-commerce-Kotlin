package com.example.e_commercekotlin.domain

import com.example.e_commercekotlin.DatabaseHelper
import com.example.e_commercekotlin.data.DatabaseHelperImpl
import com.example.e_commercekotlin.data.model.Product

class DatabaseRepo  {

    lateinit var databaseHelper: DatabaseHelper


    suspend fun getProducts() : List<Product>{
        return databaseHelper.getProducts()
    }

    suspend fun insertProducts(products : List<Product>){
        databaseHelper.insertProducts(products)
    }
}