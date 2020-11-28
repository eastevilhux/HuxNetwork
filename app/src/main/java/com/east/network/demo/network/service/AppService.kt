package com.east.network.demo.network.service

import com.hb.travel.entity.Event
import com.hb.travel.entity.KeySet
import com.yunkai.framework.network.entity.Result
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.interfaces.RSAKey

interface AppService {

    /**
     * 发送短信验证码
     */
    @POST("api/member/sms/codes/{appId}/{phone}")
    fun sendVerifyCode(@Path("appId") appId: String, @Path("phone") phone: String): Result<Any>


    @POST("life/appbeforehand")
    fun appBeforehand() : Result<KeySet>;


    @POST("event/eventlist")
    fun homeBannerList(@Query("type") type : Int,@Query("state") state : Int,
                       @Query("appType") appType : Int) : Result<MutableList<Event>>;
}