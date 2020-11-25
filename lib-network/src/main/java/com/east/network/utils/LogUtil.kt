package com.east.network.utils

import android.util.Log
import com.east.network.BuildConfig

object LogUtil {
    private var debug = false

    fun e(tag: String?, msg: String) {
        if (isDebug()) {
            Log.e(tag, msg)
        }
    }

    fun i(tag: String?, msg: String) {
        if (isDebug()) {
            Log.i(tag, msg)
        }
    }

    fun d(tag: String?, msg: String) {
        if (isDebug()) {
            Log.d(tag, msg)
        }
    }

    fun w(tag: String?, msg: String) {
        if (isDebug()) {
            Log.w(tag, msg)
        }
    }

    fun isDebug(): Boolean {
        return BuildConfig.DEBUG;
    }

    fun setDebug(debug: Boolean) {
        LogUtil.debug = debug
    }
}