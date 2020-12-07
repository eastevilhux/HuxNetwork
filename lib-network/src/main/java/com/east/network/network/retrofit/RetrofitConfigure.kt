package com.east.network.network.retrofit

import com.east.network.network.NetworkHelper
import com.east.network.network.interceptor.HttpInterceptor
import com.east.network.network.interceptor.LogInterceptor
import com.east.network.network.interceptor.NetInterceptor
import com.east.network.network.interceptor.ParamsInterceptor
import com.east.network.network.retrofit.adapter.BaseCallAdapterFactory
import com.east.network.network.retrofit.convert.BaseConverterFactory
import com.east.network.utils.JsonUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitConfigure {
    companion object{
        fun createByRetrofit(retrofit:Retrofit,map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java){
                var httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())
                val okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .addInterceptor(HttpInterceptor())
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addInterceptor(ParamsInterceptor())
                    .addNetworkInterceptor(HttpInterceptor())
                    .build()

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = retrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }


        fun createByUrl(baseUrl:String,map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java){
                var httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())
                val okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .addInterceptor(HttpInterceptor())
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addInterceptor(ParamsInterceptor())
                    .addNetworkInterceptor(HttpInterceptor())
                    .build()

                val mRetrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(
                        BaseConverterFactory.create(
                            JsonUtil.instance.getGson()
                        )
                    ) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .client(okHttpClient)
                    .build()

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = mRetrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }

        fun registerService(map: HashMap<String, Class<*>>): HashMap<String, Any> {
            synchronized(RetrofitConfigure::class.java) {
                var httpInterceptor = HttpInterceptor();
                httpInterceptor.addHeader(NetworkHelper.instance().httpHeader())
                val okHttpClient = OkHttpClient().newBuilder()
                    .connectTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .readTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .writeTimeout(
                        NetworkHelper.instance().httpConfig().timeOut(),
                        TimeUnit.SECONDS
                    )
                    .addInterceptor(httpInterceptor)
                    .addInterceptor(LogInterceptor())
                    .addInterceptor(NetInterceptor())
                    .addInterceptor(ParamsInterceptor())
                    .addNetworkInterceptor(HttpInterceptor())
                    .build()

                val mRetrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(NetworkHelper.instance().httpConfig().baseUrl())
                    .addConverterFactory(
                        BaseConverterFactory.create(
                            JsonUtil.instance.getGson()
                        )
                    ) //添加gson转换器
                    .addCallAdapterFactory(BaseCallAdapterFactory()) //添加rxjava转换器
                    .client(okHttpClient)
                    .build()

                val m = HashMap<String, Any>(map.size)
                for(en in map){
                    val t = mRetrofit.create(en.value);
                    m[en.key] = t;
                }
                return m
            }
        }
    }
}