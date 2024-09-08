package com.example.e_commercekotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commercekotlin.data.model.Product

@Database(entities = [Product::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
    abstract fun ProductDao(): ProductDao
}
