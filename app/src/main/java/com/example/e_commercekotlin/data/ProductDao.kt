package com.example.e_commercekotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.e_commercekotlin.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>
    @Insert
    suspend fun insertAll(product: List<Product>)
}