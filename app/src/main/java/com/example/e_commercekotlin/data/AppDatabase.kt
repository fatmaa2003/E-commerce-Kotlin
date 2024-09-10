package com.example.e_commercekotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.e_commercekotlin.Util.Converters
import com.example.e_commercekotlin.data.model.Cart

@Database(entities = [Cart::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao



    companion object{
        private var databaseManager: AppDatabase?= null

        fun init(context: Context){
            if (databaseManager==null){
                synchronized(AppDatabase::class.java){
                    databaseManager= Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "news_database").allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
        }
        fun getInstance(): AppDatabase{
            return databaseManager!!
        }
    }

}