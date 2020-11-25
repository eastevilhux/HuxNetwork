package com.east.network.network.retrofit.convert

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.east.network.network.NetworkHelper
import com.east.network.utils.LogUtil
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter

class BaseReqeustBodyConverter<T>(private val gson:Gson, val adapter: TypeAdapter<T>) : Converter<T,RequestBody>{
    companion object{
        private val MEDIA_TYPE: MediaType? = MediaType.parse("text/application/json; charset=UTF-8");
        const val TAG = "YKReqeustBodyConverter==>";
    }


    @SuppressLint("LongLogTag")
    override fun convert(value: T): RequestBody {
        LogUtil.d(TAG,"convert");
        val buffer = Buffer();
        val writer = OutputStreamWriter(buffer.outputStream(),
            NetworkHelper.instance().httpConfig().httpCharset());
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

}