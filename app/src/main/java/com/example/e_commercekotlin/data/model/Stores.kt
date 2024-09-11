package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

class Stores : ArrayList<Stores.StoresItem>() {

    data class StoresItem(
        @SerializedName("imageurl")
        val imageurl: Int?,
        @SerializedName("name")
        val name: String?
    )

}
