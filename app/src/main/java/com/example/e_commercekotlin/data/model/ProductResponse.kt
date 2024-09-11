package com.example.e_commercekotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class ProductResponse : ArrayList<ProductResponse.ProductResponseItem>() {
    @Entity(tableName = "products_table")
    data class ProductResponseItem(
        @SerializedName("description")
        val description: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?,
        @SerializedName("manufacturer")
        val manufacturer: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("productId")
        @PrimaryKey(autoGenerate = true)
        val productId: Int?,
        @SerializedName("productName")
        val productName: String?,
        @SerializedName("stockQuantity")
        val stockQuantity: Int?,
        @SerializedName("warrantyPeriod")
        val warrantyPeriod: Int?,
        @SerializedName("categoryId")
        val categoryId: Int?
    ) {
        data class ProductImage(
            @SerializedName("imageId")
            val imageId: Int?,
            @SerializedName("imageUrl")
            val imageUrl: String?
        )
    }
}
