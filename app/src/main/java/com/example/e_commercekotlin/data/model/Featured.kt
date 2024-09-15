package com.example.e_commercekotlin.presentation.model

data class Featured(
    val imageResId: Int,
    val title: String,
    val description: String="",
    val price: String = ""
)
