package com.east.network.utils

import com.east.network.utils.JsonUtil
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun String.isJson() : Boolean{
    val jsonElement: JsonElement?
    try {
        jsonElement = JsonParser().parse(this)
    } catch (e: Exception) {
        return false
    }
    if (jsonElement == null) {
        return false
    }
    return jsonElement.isJsonObject
}

/**
 * 转换成json格式
 */
fun Any.toJSON(): String? = JsonUtil.instance.getGson().toJson(this);


fun delay(long: Long, scope: GlobalScope = GlobalScope, block: () -> Unit) = scope.launch(
    Dispatchers.Main) {
    kotlinx.coroutines.delay(long)
    block()
}

