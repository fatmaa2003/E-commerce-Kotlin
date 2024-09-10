package com.example.e_commercekotlin.Util

import androidx.room.TypeConverter
import com.example.e_commercekotlin.data.model.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromProductList(productList: List<Cart.Product>?): String? {
        return gson.toJson(productList)
    }

    @TypeConverter
    fun toProductList(productString: String?): List<Cart.Product>? {
        val listType = object : TypeToken<List<Cart.Product>>() {}.type
        return gson.fromJson(productString, listType)
    }

}