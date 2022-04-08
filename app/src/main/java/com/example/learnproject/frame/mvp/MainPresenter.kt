package com.example.learnproject.frame.mvp

import com.example.learnproject.MainActivity

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MainPresenter(private val view: MVPActivity?) : MainContract.P {

    private val mainModel = MainModel(this)

    override fun requestDownLoadBitmap(bean: ImageBean) {
        mainModel.downLoadBitmap(bean)
    }

    override fun resposeDownLoadBitmap(isSuccess: Boolean, bean: ImageBean) {
        view?.runOnUiThread {
            view.resposeDownLoadBitmap(isSuccess, bean)
        }
    }

}