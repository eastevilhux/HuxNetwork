package com.east.network.network

import com.east.network.network.interceptor.HttpInterceptor
import com.east.network.network.retrofit.model.AppModel
import java.util.*

class NetworkHelper private constructor(builder: HttpConfig.Builder){
    private var httpHelper : HttpInterceptor.HttpHeader? = null;

    companion object{
        @Volatile
        private var myself: NetworkHelper? = null
        fun init(builder: HttpConfig.Builder): NetworkHelper {
            if (myself == null) {
                synchronized(NetworkHelper::class) {
                    if (myself == null) {
                        myself = NetworkHelper(builder)
                        initHttpConfig(builder);
                    }
                }
            }
            AppModel.initModel();
            return myself!!
        }

        @Volatile
        private var mHttpConfig : HttpConfig? = null
        private fun initHttpConfig(builder: HttpConfig.Builder): HttpConfig {
            if (mHttpConfig == null) {
                synchronized(NetworkHelper::class) {
                    if (mHttpConfig == null) {
                        mHttpConfig = builder.builder();
                    }
                }
            }
            return mHttpConfig!!
        }

        fun instance(): NetworkHelper {
            return myself!!;
        }
    }

    fun httpConfig(): HttpConfig {
        return mHttpConfig!!;
    }

    fun addHttpHealder(header: HttpInterceptor.HttpHeader){
        this.httpHelper = header;
    }

    fun httpHeader(): HttpInterceptor.HttpHeader? {
        return httpHelper;
    }
}