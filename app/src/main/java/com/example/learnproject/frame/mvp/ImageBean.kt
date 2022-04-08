package com.example.learnproject.frame.mvp

import android.graphics.Bitmap

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class ImageBean(
        val path : String,
        var bitmap : Bitmap?
)