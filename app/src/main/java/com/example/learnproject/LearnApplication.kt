package com.example.learnproject

import android.app.Application
import com.example.framesealproject.base.ActivityLifecycleCallbacksImpl

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class LearnApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    }
}
