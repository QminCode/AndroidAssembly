package com.abner.common.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abner.common.base.viewmodel.BaseViewModel
import com.abner.common.network.manager.NetState
import com.abner.common.network.manager.NetworkStateManager
import com.abner.common.utils.notNull
import com.abner.common.utils.ext.getVmClazz
import me.hgj.jetpackmvvm.ext.inflateBindingWithGeneric

/**
 * @author: playboi_YzY
 * @date: 2023/3/29 21:59
 * @description:
 * @version:
 */
abstract class BaseVmActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

    lateinit var mDatabind: DB

    fun layoutId() = 0

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBind().notNull({
            setContentView(it)
        }, {
            setContentView(layoutId())
        })
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observeInActivity(this, Observer {
            onNetworkStateChanged(it)
        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observeInActivity(this, Observer {
            showLoading(it)
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
            dismissLoading()
        })
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observeInActivity(this, Observer {
                showLoading(it)
            })
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observeInActivity(this, Observer {
                dismissLoading()
            })
        }
    }

    /**
     * 初始化Databinding操作
     */
    open fun initDataBind(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }
}