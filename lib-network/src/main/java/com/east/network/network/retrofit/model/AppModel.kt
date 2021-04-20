package com.east.network.network.retrofit.model

import com.east.network.network.retrofit.RetrofitConfigure
import com.east.network.network.retrofit.service.AppService

class AppModel {
    companion object{

        private const val SERVICE_KEY_APP = "appService";
        private const val SERVICE_KEY_ACCOUNT = "accountService";


        val instance : AppModel by lazy(mode=  LazyThreadSafetyMode.SYNCHRONIZED){
            AppModel();
        }

        fun initModel(){
            instance.map;
        }
    }

    private val map : HashMap<String, Any> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        initRetrofit();
    };

    private fun initRetrofit():HashMap<String,Any>{
        var m = HashMap<String, Class<*>>();
        m.put(SERVICE_KEY_ACCOUNT, AppService::class.java)
        var map =  RetrofitConfigure.registerService(m);
        return map;
    }
}
