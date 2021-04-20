package com.east.network.network.retrofit.adapter

import com.east.network.network.NetworkHelper
import com.east.network.network.entity.Result
import com.east.network.utils.LogUtil
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class BaseCallAdapter<R>(private val type: Type) : CallAdapter<R, R> {

    override fun adapt(call: Call<R>): R {
        return try {
            val response = call.execute();
            if(response.isSuccessful){
                LogUtil.e(TAG,"response_result:SUCCESS");
                var body = response.body() ?: emptyResponse();
                val result = body as Result<*>;
                when(result.code){
                    NetworkHelper.instance().httpConfig().loginErrorCode() -> loginError();
                }
                return body;
            }else{
                LogUtil.e(TAG,"response_result:error");
                errorResponse(response);
            }
        }catch (e:Exception){
            e.printStackTrace();
            LogUtil.e(TAG,"response_result:error");
            parseException(e)
        }
    }

    override fun responseType(): Type = type;

    private fun loginError(): R {
        LogUtil.d(TAG,"login_error");
        return exception(
            Result<Any>(),"login_error",
            NetworkHelper.instance().httpConfig().loginErrorCode()) as R;
    }

    private fun errorResponse(response: Response<R>) : R{
        LogUtil.d(TAG,"errorResponse");
        return error(Result<Any>(),response) as R;
    }

    private fun emptyResponse() : R{
        LogUtil.d(TAG,"emptyResponse");
        return empty(Result<Any>()) as R;
    }

    private fun parseException(e:Exception) : R{
        LogUtil.d(TAG,e?.let { e.message }?:"no error message")
        return when(e){
            is IOException,
            is ConnectException,
            is UnknownHostException,
            is SocketTimeoutException -> networkError()
            else -> exceptionResponse(e.message);
        }
    }


    private fun networkError() : R{
        LogUtil.d(TAG,"networkError");
        return exception(
            Result<Any>(),"network_error",
            NetworkHelper.instance().httpConfig().networkErrorCode()) as R;
    }

    private fun exceptionResponse(message: String?) : R{
        LogUtil.d(TAG,"exceptionResponse");
        return exception(Result<Any>(),message, NetworkHelper.instance().httpConfig().serviceErrorCode()) as R;
    }

    companion object{
        private const val TAG = "YKCallAdapter=>";

        fun <T : Result<*>> empty(response: T, code: Int = NetworkHelper.instance().httpConfig().emptyCode()): T {
            response.code = code
            response.msg = "";
            return response
        }


        fun <T : Result<*>> error(response: T, retrofitResponse: Response<*>): T {
            response.code = retrofitResponse.code()
            response.msg = retrofitResponse.message()
            return response
        }

        fun <T : Result<*>> exception(
            response: T,
            message: String?,
            code: Int = NetworkHelper.instance().httpConfig().emptyCode()
        ): Result<*> {
            response.code = code
            response.msg = message
            return response
        }
    }

}
