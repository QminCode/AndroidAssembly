package com.abner.common.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author: playboi_YzY
 * @date: 2023/3/31 16:32
 * @description: 自定义的Boolean类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 * @version:
 */
class BooleanLiveData {
    class BooleanLiveData : MutableLiveData<Boolean>() {

        override fun getValue(): Boolean {
            return super.getValue() ?: false
        }
    }
}