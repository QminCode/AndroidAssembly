package com.abner.common.utils.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.abner.common.callback.livedata.BooleanLiveData

/**
 * 作者　: hegaojian
 * 时间　: 20120/1/7
 * 描述　:
 */
object KtxAppLifeObserver : LifecycleObserver {

    var isForeground = BooleanLiveData.BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        isForeground.value = false
    }

}