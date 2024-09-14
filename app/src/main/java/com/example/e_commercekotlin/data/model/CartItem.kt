package com.example.e_commercekotlin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val products: List<ProductItem>,
    val totalCartPrice: Double,
    val cartSize: Int
) : Parcelable

@Parcelize
data class ProductItem(
    val productId: Int,
    val productName: String,
    val productMainImage: String,
    val productPrice: Double,
    val quantity: Int,
    val itemTotalPrice: Double
) : Parcelable
