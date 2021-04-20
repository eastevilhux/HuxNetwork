package com.east.network.network.retrofit.service

import com.east.network.network.entity.Result
import retrofit2.http.POST
import retrofit2.http.Path

interface AppService {

    /**
     * 发送短信验证码
     */
    @POST("api/member/sms/codes/{appId}/{phone}")
    fun sendVerifyCode(@Path("appId") appId: String, @Path("phone") phone: String): Result<Any>
}
