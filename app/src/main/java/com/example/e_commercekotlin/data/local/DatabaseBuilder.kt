package com.example.e_commercekotlin.data.local

import android.content.Context
import androidx.room.Room
import com.example.e_commercekotlin.Util.Constants
import com.example.e_commercekotlin.data.AppDatabase

object DatabaseBuilder {
    private var INSTANCE: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
}


