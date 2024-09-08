package com.example.e_commercekotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commercekotlin.data.model.ProductResponse

@Database(entities = [ProductResponse.ProductResponseItem::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
    abstract fun ProductDao(): ProductDao
}
