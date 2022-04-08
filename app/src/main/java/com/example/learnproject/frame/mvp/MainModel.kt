package com.example.learnproject.frame.mvp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.learnproject.Constant
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MainModel(val presenter: MainPresenter) : MainContract.M {

    override fun downLoadBitmap(bean: ImageBean) {
        Thread(DownTask(bean)).start()
        // thread { DownTask(bean) }.start()
    }

    inner class DownTask(private val bean: ImageBean) : Runnable {
        override fun run() {
            try {
                val url = URL(bean.path)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                if (connection.responseCode == Constant.SUCCESS) {
                    val bitmap = BitmapFactory.decodeStream(connection.inputStream)
                    setUi(Constant.SUCCESS, bitmap = bitmap)
                } else {
                    setUi(Constant.ERROR, null)
                }
            } catch (e: Exception) {
                setUi(Constant.ERROR, null)
            }

        }

        private fun setUi(code: Int, bitmap: Bitmap?) {
            bean.bitmap = bitmap
            presenter.resposeDownLoadBitmap(code == Constant.SUCCESS, bean)
        }
    }
}