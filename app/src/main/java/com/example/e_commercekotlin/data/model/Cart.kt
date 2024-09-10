package com.example.e_commercekotlin.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId : Int?=null,
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("totalCartPrice")
    val totalCartPrice: Double?
) {
    @Entity
    data class Product(
        @SerializedName("itemTotalPrice")
        val itemTotalPrice: Double?,
        @PrimaryKey
        @SerializedName("productId")
        val productId: Int?,
        @SerializedName("productMainImage")
        val productMainImage: String?,
        @SerializedName("productName")
        val productName: String?,
        @SerializedName("productPrice")
        val productPrice: Double?,
        @SerializedName("quantity")
        val quantity: Int?
    )
}