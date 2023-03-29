package com.abner.common.network.manager

import com.abner.common.livedata.EventLiveData

/**
 * @author: playboi_YzY
 * @date: 2023/3/29 22:08
 * @description: 网络状态管理者
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