package com.east.network.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.east.network.demo.network.model.AppModel
import com.east.network.utils.LogUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test();
    }

    private fun test(){
        myThread.start();
    }

    var myThread = object : Thread() {
        override fun run() {
            super.run()
            var beforResult = AppModel.instance.appBeforehand();
            LogUtil.d("TEST==>","result=>${beforResult.code}___${beforResult.data?.appkey}")
            var result = AppModel.instance.appHomeBanner();
            LogUtil.d("TEST==>","result=>${result.code}___${result.data?.size}")
        }
    }

}