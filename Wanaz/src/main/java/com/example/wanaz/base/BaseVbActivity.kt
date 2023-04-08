package com.example.wanaz.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.abner.common.base.activity.BaseVmVbActivity
import com.abner.common.base.viewmodel.BaseViewModel

/**
 * @author: playboi_YzY
 * @date: 2023/4/6 18:43
 * @description:
 * @version:
 */
abstract class BaseVbActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVmVbActivity<VM, VB>()  {
    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        //showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        //dismissLoadingExt()
    }

    /* *//**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     *//*
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }*/
}