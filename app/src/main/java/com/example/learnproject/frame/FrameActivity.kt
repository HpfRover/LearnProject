package com.example.learnproject.frame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnproject.MainRecyAdapter
import com.example.learnproject.R
import com.example.learnproject.frame.mvp.MVPActivity
import com.example.learnproject.util.Util
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   : Android 架构学习 mvc mvp mvvm
 *     version: 1.0
 * </pre>
 */
class FrameActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        initView()
    }


    private val list = mutableListOf(
            "mvc 架构",
    )

    private val userClick: (Int) -> Unit = { pos ->
        when (pos) {
             0 -> Util.startActivity<MVPActivity>(this)
        }

    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recy.layoutManager = linearLayoutManager
        recy.adapter = MainRecyAdapter(list, userClick)
    }
}