package com.example.e_commercekotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddToCartRequest(
    @PrimaryKey
    val productId: Int,
    val quantity: Int
)
