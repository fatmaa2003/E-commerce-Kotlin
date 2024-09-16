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
    val productId: Long,
    val productName: String,
    val productMainImage: String,
    var productPrice: Double,
    var quantity: Int,
    val itemTotalPrice: Double
) : Parcelable
