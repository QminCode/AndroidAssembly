package com.abner.common.network.manager

import com.abner.common.base.livedata.EventLiveData

/**
 * @author: playboi_YzY
 * @date: 2023/3/31 20:58
 * @description: 网络变化管理者
 * @version:
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}