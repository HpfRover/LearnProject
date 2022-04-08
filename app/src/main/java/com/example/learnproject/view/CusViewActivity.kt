package com.example.learnproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnproject.MainRecyAdapter
import com.example.learnproject.R
import com.example.learnproject.frame.mvp.MVPActivity
import com.example.learnproject.util.Util
import com.example.learnproject.view.cover.CoverViewActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   : Android 架构学习 mvc mvp mvvm
 *     version: 1.0
 * </pre>
 */
class CusViewActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        initView()
    }


    private val list = mutableListOf(
            "自定义 CoverViewLayout",
    )

    private val userClick: (Int) -> Unit = { pos ->
        when (pos) {
             0 -> Util.startActivity<CoverViewActivity>(this)
        }

    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recy.layoutManager = linearLayoutManager
        recy.adapter = MainRecyAdapter(list, userClick)
        LinearLayoutManager.HORIZONTAL
    }
}