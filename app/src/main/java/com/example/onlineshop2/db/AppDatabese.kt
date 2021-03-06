package com.example.onlineshop2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineshop2.model.CategoryModel
import com.example.onlineshop2.model.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class],version = 1)
abstract class AppDatabese:RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao


    companion object{
        var INSTANCE:AppDatabese?=null

        fun initDatabase(context:Context){
            if (INSTANCE == null){
                synchronized(AppDatabese::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabese::class.java, "online_shop_db").allowMainThreadQueries().build()

                }
            }
        }


        fun getDatabase(): AppDatabese{
            return INSTANCE!!
        }
    }

}