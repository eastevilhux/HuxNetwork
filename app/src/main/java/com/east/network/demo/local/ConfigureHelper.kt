package com.east.network.demo.local

import com.east.network.demo.network.service.AppService
import com.east.network.network.HttpConfig
import com.east.network.network.NetworkHelper
import com.east.network.network.datasource.DataHelper
import com.east.network.network.interceptor.HttpInterceptor
import com.east.network.network.retrofit.RetrofitConfigure
import com.east.network.utils.LogUtil
import java.util.*
import kotlin.collections.HashMap


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
        "http://192.168.0.129:8080/lifehouse/"

        private const val SERVICE_KEY_ACCOUNT = "accountService";
        private const val SERVICE_KEY_APP = "eastappservice";
    }

    fun init(){
        DataHelper.instance.setRSA();
        LogUtil.setDebug(true);
        initNetwork();
        map;
    }

    private val map : HashMap<String, Any> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        initRetrofit();
    };

    private fun initNetwork(){
        LogUtil.setDebug(true);
        var builder = HttpConfig.Builder(baseUrl)
            .charset("UTF-8")
            .networkErrorCode(404);
        NetworkHelper.init(builder);
        NetworkHelper.instance().addHttpHealder(header);
    }


    private fun initRetrofit(): java.util.HashMap<String, Any> {
        var m = HashMap<String, Class<*>>();
        m.put(SERVICE_KEY_APP, AppService::class.java);
        //使用默认配置
        var map = RetrofitConfigure.registerService(m);
        return map;
    }

    val appService : AppService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        map[SERVICE_KEY_APP] as AppService;
    }


    var header = object : HttpInterceptor.HttpHeader{
        override fun getHealder(): TreeMap<String, String> {
            var map = TreeMap<String,String>();
            return map;
        }
    }
}