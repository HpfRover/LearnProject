package com.example.learnproject.util

import android.content.Context
import android.content.Intent

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object Util {
    // 启动 Activity
    inline fun <reified T> startActivity(context: Context) {
        context.startActivity(Intent(context, T::class.java))
    }
}