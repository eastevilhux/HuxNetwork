package com.east.network.utils

import android.util.Log

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
        return debug;
    }

    fun setDebug(debug: Boolean) {
        LogUtil.debug = debug
    }
}