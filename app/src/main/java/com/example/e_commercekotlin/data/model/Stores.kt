package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

class Stores : ArrayList<Stores.StoresItem>() {

    data class StoresItem(
        @SerializedName("description")
        val description: String?,
        @SerializedName("discount")
        val discount: Double?,
        @SerializedName("imageurl")
        val imageurl: String?,
        @SerializedName("market_image")
        val marketImage: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("store_id")
        val storeId: Int?
    )
}
