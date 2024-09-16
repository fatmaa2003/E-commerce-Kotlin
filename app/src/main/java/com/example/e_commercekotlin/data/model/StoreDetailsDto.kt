package com.example.e_commercekotlin.data.model

data class StoreDetailsDto(
    val category: Category,
    val message: String,
    val statusCode: Int
) {
    data class Category(
        val categoryid: Int,
        val description: String,
        val discount: Double,
        val image_url: String,
        val marketImage: String,
        val name: String,
        val products: List<ProductResponse.ProductResponseItem>
    ) {

    }
}