package com.example.e_commercekotlin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commercekotlin.data.model.Cart
@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    fun getAllCart(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart)
}