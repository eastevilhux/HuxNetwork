package com.east.network.network.retrofit.convert

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.NullPointerException
import java.lang.reflect.Type

class BaseConverterFactory(private val gson:Gson) : Converter.Factory(){

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        var adapter = gson.getAdapter(TypeToken.get(type));
        return BaseResponseBodyConverter(gson,adapter);
    }

    companion object{
        private  val TAG = "BaseConverterFactory=>";

        fun create() : BaseConverterFactory {
            return create(Gson());
        }

        fun create(gson:Gson?) : BaseConverterFactory {
            if(gson == null) {
                Log.e(TAG,"create EastConverterFactory when gson is null")
                throw NullPointerException("gson not allow null")
            };
            return BaseConverterFactory(gson);
        }
    }
}
