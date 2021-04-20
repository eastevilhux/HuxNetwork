package com.east.network.demo.network.model

import com.east.network.demo.local.ConfigureHelper
import com.east.network.demo.network.service.AppService
import com.hb.travel.entity.Event
import com.hb.travel.entity.KeySet
import com.east.network.network.entity.Result

class AppModel private constructor(){
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AppModel() }

        private val appService : AppService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ConfigureHelper.instance.appService }
    }

    fun appBeforehand(): Result<KeySet> {
        return appService.appBeforehand();
    }

    fun appHomeBanner(): Result<MutableList<Event>> {
        return appService.homeBannerList(type = 2,state = 0,appType = 2)
    }
}

