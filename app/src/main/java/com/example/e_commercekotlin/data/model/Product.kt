package com.example.e_commercekotlin.data.model

import androidx.room.Entity

@Entity
data class Product(
    val title: String,
    val price: Int,
    val images: List<String>
)
