package com.example.e_commercekotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.e_commercekotlin.data.model.ProductResponse

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductResponseItem")
    suspend fun getAllProducts(): List<ProductResponse.ProductResponseItem>
    @Insert
    suspend fun insertAll(product: List<ProductResponse.ProductResponseItem>)

}