package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

class Stores : ArrayList<Stores.StoresItem>() {

    data class StoresItem(

        val imageurl: String,
        @SerializedName("name")
        val name: String?

    )
}
