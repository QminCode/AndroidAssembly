package com.abner.common.callback.livedata

import androidx.lifecycle.MutableLiveData


/**
 * @author: playboi_YzY
 * @date: 2023/3/31 16:33
 * @description: 自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 * @version:
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}