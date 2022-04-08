package com.example.framesealproject.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * <pre>
 *     @author : hpf
 *     time   : 2020/11/26
 *     desc   : 实现 ActivityLifecycleCallbacks 接口，对 Activity 生命周期进行统一监听，并交付给 ActivityManager 管理
 *     version: 1.0
 * </pre>
 */
@SuppressLint("LongLogTag")
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    companion object {
        private const val TAG_APPLICATION = "LearnApplication : Activity >> "
    }

    private val activityManager = ActivityManager.instance

    override fun onActivityPaused(activity: Activity) {
         Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityPaused")
    }

    override fun onActivityStarted(activity: Activity) {
        // Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityDestroyed")
        activityManager.pop(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Log.d(TAG_APPLICATION,"${activity.componentName.className}  :  onActivityStarted")
    }

    override fun onActivityStopped(activity: Activity) {
         Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityCreated")
        activityManager.put(activity)
    }

    override fun onActivityResumed(activity: Activity) {
         Log.d(TAG_APPLICATION, "${activity.componentName.className}  :  onActivityResumed")
    }

}