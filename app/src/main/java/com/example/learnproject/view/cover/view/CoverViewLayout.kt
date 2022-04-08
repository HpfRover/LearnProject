package com.example.learnproject.view.cover.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.example.learnproject.R
import com.example.learnproject.view.cover.CoverLayer
import com.example.learnproject.view.cover.layout.CoverLayoutManager
import com.example.learnproject.view.cover.layout.CoverStartLeftLayoutManager

/**
 * <pre>
 *     @author : hpf
 *     time   : 2022/04/06
 *     desc   :
 * </pre>
 */
@SuppressLint("Recycle")
class CoverViewLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
    private var orientation: Int = CoverLayoutManager.START_LEFT
    private val manager : CoverLayoutManager by lazy(LazyThreadSafetyMode.NONE) {
        getLayoutManager()
    }

    init {
        // fixme : 系统的获取调用的是传入四个参数的方法
        val a = context.obtainStyledAttributes(attrs, R.styleable.CoverViewLayout)
        orientation = a.getInt(R.styleable.CoverViewLayout_cover_orientation, CoverLayoutManager.START_LEFT)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val pair = manager.measureCoverLayout(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(pair.first, pair.second)
    }

    // fixme : 重写 generateLayoutParams


    private fun getLayoutManager(): CoverLayoutManager {
        return when (orientation) {
            CoverLayoutManager.START_LEFT -> CoverStartLeftLayoutManager()
            /*CoverLayoutManager.START_RIGHT -> CoverStartRightLayoutManager()
            CoverLayoutManager.START_TOP -> CoverStartTopLayoutManager()
            CoverLayoutManager.START_BOTTOM -> CoverStartBottomLayoutManager()*/
            else -> CoverStartLeftLayoutManager()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        manager.onLayoutCoverLayout(this)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context,attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        if(p is LayoutParams) {
            return p
        }else if(p is MarginLayoutParams) {
            return p
        }
        return p
    }


    class LayoutParams : MarginLayoutParams {
        // 依赖 id
        var coverRelativeId = 0
        // 与依赖的 view 的交叉范围
        var coverRelativeCross = -1
        // 是否以依赖的 view 作为 margin 的基准
        var coverMarginRelative = true
        // 与依赖的 view 的层级关系
        var coverRelativeLayer = CoverLayer.COVER_LAYER_TOP

        constructor(width: Int, height: Int) : super(width, height)

        constructor(source: ViewGroup.LayoutParams) : super(source)

        constructor(source: ViewGroup.MarginLayoutParams) : super(source)

        // 主要构造方法，从布局中获取自定义属性
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            val attr = context.obtainStyledAttributes(attrs,R.styleable.CoverViewLayout_Layout)

            coverRelativeId = attr.getResourceId(R.styleable.CoverViewLayout_Layout_layout_cover_relative,0)
            coverRelativeCross = attr.getDimensionPixelSize(R.styleable.CoverViewLayout_Layout_layout_cover_cross,-1)
            coverMarginRelative = attr.getBoolean(R.styleable.CoverViewLayout_Layout_layout_cover_margin_relative,true)
            coverRelativeLayer = attr.getInt(R.styleable.CoverViewLayout_Layout_layout_cover_layer,CoverLayer.COVER_LAYER_TOP)

            attr.recycle()
        }

    }

}