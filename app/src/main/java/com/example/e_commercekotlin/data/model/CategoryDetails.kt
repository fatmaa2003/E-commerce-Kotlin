package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

class CategoryDetails : ArrayList<CategoryDetails.CategoryDetailsItem>(){
    data class CategoryDetailsItem(
        @SerializedName("categoryid")
        val categoryid: Int?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("products")
        val products: List<ProductResponse.ProductResponseItem>?
    )
}