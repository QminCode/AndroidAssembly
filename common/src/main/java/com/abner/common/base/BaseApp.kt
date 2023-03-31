package com.abner.common.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author: playboi_YzY
 * @date: 2023/3/31 17:08
 * @description: 继承后可以写扩展函数，在Activity/fragment中获取Application级别的ViewModel
 * @version:
 */
class BaseApp : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}