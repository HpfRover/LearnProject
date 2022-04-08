package com.example.framesealproject.base

import android.app.Activity


/**
 * <pre>
 *     @author : hpf
 *     time   : 2020/11/26
 *     desc   : 负责对 Activity 的统一管理
 *     version: 1.0
 * </pre>
 */
class ActivityManager private constructor(){
    private val activityList = mutableListOf<Activity>()

    companion object {
        // 双重校验锁
        val instance : ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    fun put(activity : Activity){
        activityList.add(activity)
    }

    fun pop(activity: Activity) {
        if(activityList.isEmpty()) {
            return
        }
        activityList.remove(activity)
    }

    fun exit() {
        for(activity in activityList) {
            activity.finish()
        }
    }
}