package com.example.learnproject.frame.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learnproject.R
import kotlinx.android.synthetic.main.activity_mvp.*

/**
 * <pre>
 *     @author : hpf
 *     time   : 2021/05/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MVPActivity : AppCompatActivity(), MainContract.PV {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)

        initView()

        presenter = MainPresenter(this)
    }

    private fun initView() {
        bt.setOnClickListener {
            downLoadImg()
        }
    }

    private val path = "https://img1.baidu.com/it/u=3821171310,2828217812&fm=26&fmt=auto&gp=0.jpg"
    private fun downLoadImg() {
        val bean = ImageBean(path, null)
        requestDownLoadBitmap(bean)
    }


    override fun requestDownLoadBitmap(bean: ImageBean) {
        presenter.requestDownLoadBitmap(bean)
    }

    override fun resposeDownLoadBitmap(isSuccess: Boolean, bean: ImageBean) {
        Toast.makeText(this, if (isSuccess) "下载成功" else "下载失败", Toast.LENGTH_SHORT).show()
        if (isSuccess) {
            bean.bitmap?.let { iv.setImageBitmap(it) }
        }
    }
}