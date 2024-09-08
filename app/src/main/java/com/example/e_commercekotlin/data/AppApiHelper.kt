package com.example.e_commercekotlin.data

import com.example.e_commercekotlin.data.model.Product

class AppApiHelper(private val apiService: ApiService) {
    // Define methods to interact with the API
    suspend fun fetchProductsFromApi(): List<Product> {
        // Implement your API call here
        return apiService.getItems()
    }
}
