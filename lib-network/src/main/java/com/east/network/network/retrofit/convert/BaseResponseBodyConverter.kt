package com.east.network.network.retrofit.convert

import android.annotation.SuppressLint
import android.util.Log
import com.east.network.network.HttpConfig
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.east.network.network.NetworkHelper
import com.east.network.network.datasource.DataHelper
import com.east.network.utils.Base64Util
import com.east.network.utils.LogUtil
import com.yunkai.framework.network.entity.Result
import com.east.network.utils.isJson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import java.io.StringReader
import java.lang.Exception
import java.lang.reflect.Type
import java.net.URLDecoder

class BaseResponseBodyConverter<T> internal constructor(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {
    companion object {
        private const val TAG = "BaseResponseBodyConverter==>";
    }

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
        if(encryption){
            //需要数据解密
            data = DataHelper.instance.encryptData(data);
            LogUtil.d(TAG,data);
            var result = Result<T>();
            result.code = code;
            result.state = json.optBoolean("state");
            result.encryption = encryption;
            result.msg = json.optString("msg");
            result.tag = json.optString("tag");
            val type: Type = object : TypeToken<T>() {}.type
            var t = gson.fromJson<T>(data,type);
            result.data = t;
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }else{
            val type: Type = object : TypeToken<Result<T>?>() {}.type
            var result : Result<T> = gson.fromJson(data,type);
            data = result.data.toString();
            data = URLDecoder.decode(data,NetworkHelper.instance().httpConfig().charset());
            val s = String(Base64Util.decode(data), NetworkHelper.instance().httpConfig().httpCharset());
            LogUtil.d(TAG,"base64_to_string==>${s}");
            try{
                Log.d(TAG,"S_TO_JSON=>")
                val type: Type = object : TypeToken<T>() {}.type
                var t = gson.fromJson<T>(s,type);
                result.data = t;
            }catch (e:Exception){
                Log.d(TAG,"S_IS_NOTJSON=>")
                result.data = s as T;
            }
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }

        /*if(encryption){
            data = json.optString("data");
            if(data.isEmpty()){
                return adapter.fromJson(data);
            }
            data = URLDecoder.decode(data,HttpConfig.UTF8_CHARSET);
            Constants.serviceKey?.let {
                Log.d(TAG,Constants.decrypType.toString());
                when(Constants.decrypType){
                    Constants.DecrypType.DECRYPT_RSA -> data = RSAUtil.decryptByPublicKey(data,it)
                    Constants.DecrypType.DECRYPT_AES -> data = Constants.serviceKey?.let { AESUtil.aesDecrypt(data, it) };
                }
            }
            Log.d(TAG,data);
            var result = Result<T>();
            result.code = code;
            result.state = json.optBoolean("state");
            result.encryption = encryption;
            result.msg = json.optString("msg");
            result.tag = json.optString("tag");
            val type: Type = object : TypeToken<T>() {}.type
            var t = gson.fromJson<T>(data,type);
            result.data = t;
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }else {
            val type: Type = object : TypeToken<Result<T>?>() {}.type
            var result : Result<T> = gson.fromJson(data,type);
            data = result.data.toString();
            data = URLDecoder.decode(data,HttpConfig.UTF8_CHARSET);
            val s = String(Base64Util.decode(data), HttpConfig.HTTP_CHARSET);
            if(s.isJson()){
                result.data = gson.fromJson<T>(s,object : TypeToken<T?>() {}.type);
            }else{
                result.data = s as T;
            }
            var gsonData = gson.toJson(result);
            LogUtil.e(TAG,gsonData);
            val reader = StringReader(gsonData);
            return adapter.fromJson(reader)
        }*/
    }

}


