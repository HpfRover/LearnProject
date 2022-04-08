package com.example.learnproject.frame.mvp

import android.media.Image

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface MainContract {
    interface M {
        fun downLoadBitmap(bean: ImageBean)
    }

    interface P {
        fun requestDownLoadBitmap(bean: ImageBean)

        fun resposeDownLoadBitmap(isSuccess: Boolean, bean: ImageBean)
    }

    interface PV{
        fun requestDownLoadBitmap(bean: ImageBean)

        fun resposeDownLoadBitmap(isSuccess: Boolean, bean: ImageBean)
    }
}