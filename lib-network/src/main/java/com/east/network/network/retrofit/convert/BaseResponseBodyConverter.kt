package com.east.network.network.retrofit.convert

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.east.network.network.NetworkHelper
import com.east.network.network.datasource.DataHelper
import com.east.network.network.entity.Result
import com.east.network.utils.Base64Util
import com.east.network.utils.LogUtil
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import java.io.StringReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.URLDecoder

class BaseResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {
    companion object {
        private const val TAG = "BaseResponseBodyConverter==>";
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("LongLogTag")
    override fun convert(value: ResponseBody): T {
        var data = value.string();
        LogUtil.d(TAG,data);
        var json = JSONObject(data);
        var code = json.optInt("code");
        Log.d(TAG,code.toString());
        if(code != NetworkHelper.instance().httpConfig().successCode()){
            return adapter.fromJson(data);
        }
        var encryption = json.optBoolean("encryption")?:false;
        LogUtil.d(TAG,encryption.toString());
        var result = Result<T>();
        result.code = code;
        result.state = json.optBoolean("state");
        result.encryption = encryption;
        result.msg = json.optString("msg");
        result.tag = json.optString("tag");
        result.base64 = json.optBoolean("base64");
        result.urlEncoder = json.optBoolean("urlEncoder");
        var dataStr = json.optString("data");
        LogUtil.d(TAG,"RESULT_DATA=>${dataStr}")
        if(encryption){
            //需要数据解密
            Log.d(TAG,"NEED_ENCRYPT,DATA IS ==>${dataStr}");
            //判断是否需要进行URLDecode
            if(result.urlEncoder){
                dataStr = URLDecoder.decode(dataStr,NetworkHelper.instance().httpConfig().charset());
                LogUtil.d(TAG,"URLECODE_RESULT==>${dataStr}");
            }else{
                if(NetworkHelper.instance().httpConfig().isNeedURLDecode()){
                    //后端返回数据中为false，则根据当前app的配置来判断
                    dataStr = URLDecoder.decode(dataStr,NetworkHelper.instance().httpConfig().charset());
                    LogUtil.d(TAG,"URLECODE_RESULT==>${dataStr}");
                }
            }
            //进行数据解密处理
            dataStr = DataHelper.instance.decrpytData(dataStr);
            LogUtil.d(TAG,"RESULT_DECRPYT=>${dataStr}")
            val type: Type = object : TypeToken<T>() {}.type
            var t = gson.fromJson<T>(dataStr,type);
            result.data = t;
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }else{
            //判断是否需要进行URLEcode
            if(result.urlEncoder){
                //如果后端返回的urlencode字段为true,则不考虑app中的配置
                dataStr = URLDecoder.decode(dataStr,NetworkHelper.instance().httpConfig().charset());
                LogUtil.d(TAG,"URLECODE_RESULT==>${dataStr}");
            }else{
                //如果为false，则根据配置来进行判断
                if(NetworkHelper.instance().httpConfig().isNeedURLDecode()){
                    dataStr = URLDecoder.decode(dataStr,NetworkHelper.instance().httpConfig().charset());
                    LogUtil.d(TAG,"URLECODE_RESULT==>${dataStr}");
                }
            }
            //判断是否要进行BASE64处理
            if(result.base64){
                //如果后端返回的base64字段为true，则直接进行处理
                dataStr = String(Base64Util.decode(data), NetworkHelper.instance().httpConfig().httpCharset());
                LogUtil.d(TAG,"base64_to_string==>${dataStr}");
            }else {
                //根据配置进行判断是否要进行base64处理
                if (NetworkHelper.instance().httpConfig().isNeedBase64()) {
                    dataStr = String(Base64Util.decode(dataStr), NetworkHelper.instance().httpConfig().httpCharset());
                    LogUtil.d(TAG,"base64_to_string==>${dataStr}");
                }
            }
            LogUtil.d(TAG,"BEFOR_TOJSON_DATA==>${dataStr}");
            val type: Type = object : TypeToken<T>() {}.type
            var t = gson.fromJson<T>(dataStr,type);
            result.data = t;
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }
    }
}


