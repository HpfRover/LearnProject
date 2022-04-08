package com.example.learnproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnproject.frame.FrameActivity
import com.example.learnproject.util.Util
import com.example.learnproject.view.CusViewActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }


    private val list = mutableListOf(
        "Android 架构学习",
        "自定义View"
    )

    private val userClick: (Int) -> Unit = { pos ->
        when (pos) {
            0 -> Util.startActivity<FrameActivity>(this)
            1 -> Util.startActivity<CusViewActivity>(this)
        }

    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recy.layoutManager = linearLayoutManager
        recy.adapter = MainRecyAdapter(list, userClick)
    }
}