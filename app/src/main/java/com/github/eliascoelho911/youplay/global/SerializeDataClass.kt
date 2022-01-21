package com.github.eliascoelho911.youplay.global

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)

    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}