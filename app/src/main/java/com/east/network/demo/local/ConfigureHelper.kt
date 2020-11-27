package com.east.network.demo.local

import com.east.network.network.HttpConfig
import com.east.network.network.NetworkHelper
import com.east.network.network.datasource.DataHelper


class ConfigureHelper {

    companion object{
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ConfigureHelper() }

        private val baseUrl: String
            get() =
                /*when (BuildConfig.VERSION_TYPE) {
                    BuildConfig.VERSION_DEV,
                    BuildConfig.VERSION_SIT -> "http://116.63.44.150:8000/"
                    BuildConfig.VERSION_UAT -> "http://prod-video.yunkaiwangluo.net:9000/"
                    BuildConfig.VERSION_PROD -> "http://prod-video.yunkaiwangluo.net:9000/"
                    else -> throw IllegalStateException("version type error")
                }*/
        "http://116.63.44.150:8000/"
    }

    fun init(){
        initNetwork();
    }

    fun addService(){

    }

    private fun initNetwork(){
        var builder = HttpConfig.Builder(baseUrl)
            .charset("UTF-8")
            .networkErrorCode(404);
        NetworkHelper.init(builder);
    }
}