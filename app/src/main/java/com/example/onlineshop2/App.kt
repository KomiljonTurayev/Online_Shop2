package com.example.onlineshop2

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.onlineshop2.db.AppDatabese
import com.orhanobut.hawk.Hawk

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabese.initDatabase(this)
    }
}