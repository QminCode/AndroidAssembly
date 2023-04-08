package com.example.wanaz.viewmodel.state

import com.abner.common.base.viewmodel.BaseViewModel
import com.abner.common.callback.databinding.IntObservableField
import com.abner.common.callback.databinding.StringObservableField

/**
 * @author: playboi_YzY
 * @date: 2023/4/6 16:04
 * @description:
 * @version:
 */
class MeViewModel : BaseViewModel(){
    var name = StringObservableField("请先登录~")

    var integral = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

}