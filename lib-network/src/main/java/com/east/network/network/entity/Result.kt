package com.yunkai.framework.network.entity

import com.east.network.network.NetworkHelper

class Result<T>(var data:T? = null) {

    var code = 0;
    var tag : String? = null;
    var msg : String? = null;
    var encryption : Boolean = false;
    var state : Boolean = false;

    /**
     * 扩展参数
     */
    var extended : String? = null;

    val isSuccess
        get() = code == NetworkHelper.instance().httpConfig().successCode();

}