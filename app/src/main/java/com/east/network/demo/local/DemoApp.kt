package com.east.network.demo.local

import android.app.Application

class DemoApp : Application() {


    override fun onCreate() {
        super.onCreate()
        ConfigureHelper.instance.init();
    }
}