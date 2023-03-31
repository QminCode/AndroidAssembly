package com.abner.common.utils.ext

import android.util.Log

/**
 * @author: playboi_YzY
 * @date: 2023/3/31 16:41
 * @description: 日志打印扩展函数，根据true|false 控制网络请求日志和该框架产生的打印
 * @version:
 */

const val TAG = "App"

var AppLog = true

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG) =
    log(LEVEL.V, tag, this)
fun String.logd(tag: String = TAG) =
    log(LEVEL.D, tag, this)
fun String.logi(tag: String = TAG) =
    log(LEVEL.I, tag, this)
fun String.logw(tag: String = TAG) =
    log(LEVEL.W, tag, this)
fun String.loge(tag: String = TAG) =
    log(LEVEL.E, tag, this)

private fun log(level: LEVEL, tag: String, message: String) {
    if (!AppLog) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}