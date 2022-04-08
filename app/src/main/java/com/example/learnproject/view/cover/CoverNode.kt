package com.example.learnproject.view.cover

import android.view.View

/**
 * <pre>
 *     @author : hpf
 *     time   : 2022/04/07
 *     desc   : 链表结点
 * </pre>
 */
class CoverNode {
    var view: View? = null

    // 与依赖的 view 的层级关系
    var layer: Int = CoverLayer.COVER_LAYER_TOP

    // 是否有依赖的 view
    var coverIsRelative: Boolean = false

    // 依赖的 view 的 id
    var coverRelativeId: Int = 0

    // 是否以依赖的 view 作为 margin 基准
    var coverMarginRelative: Boolean = true

    // 与依赖的 view 的交叉范围
    var coverCross: Int = -1

    // 后续结点
    var nextNodes: MutableList<CoverNode> = mutableListOf()

    // 依赖的上一个结点
    // var preNode : CoverNode? = null


    // 顶点 (布局和绘制的时候需要)
    var lLeft = 0

    var lTop = 0

    var lRight = 0

    var lBottom = 0

    override fun toString(): String {
        return "CoverNode(view=$view, coverRelativeId=$coverRelativeId, coverMarginRelative=$coverMarginRelative, coverCross=$coverCross, lLeft=$lLeft, lTop=$lTop , lRight=$lRight , lBottom=$lBottom)"
    }
}


object CoverLayer {
    const val COVER_LAYER_TOP = 0
    const val COVER_LAYER_BOTTOM = 1
}

