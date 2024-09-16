package com.example.e_commercekotlin.data.model


import com.google.gson.annotations.SerializedName

class FreshCollection : ArrayList<FreshCollection.FreshCollectionItem>(){
    data class FreshCollectionItem(
        @SerializedName("description")
        val description: String?,
        @SerializedName("freshCollection")
        val freshCollection: Boolean?,
        @SerializedName("freshSince")
        val freshSince: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?,
        @SerializedName("manufacturer")
        val manufacturer: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("productId")
        val productId: Int?,
        @SerializedName("productImages")
        val productImages: List<ProductImage?>?,
        @SerializedName("productName")
        val productName: String?,
        @SerializedName("stockQuantity")
        val stockQuantity: Int?,
        @SerializedName("warrantyPeriod")
        val warrantyPeriod: Int?
    ) {
        data class ProductImage(
            @SerializedName("imageId")
            val imageId: Int?,
            @SerializedName("imageUrl")
            val imageUrl: String?
        )
    }
}