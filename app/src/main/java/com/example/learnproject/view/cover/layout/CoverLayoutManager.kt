package com.example.learnproject.view.cover.layout

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.example.learnproject.view.cover.CoverNode
import com.example.learnproject.view.cover.view.CoverViewLayout

/**
 * <pre>
 *     @author : hpf
 *     time   : 2022/04/07
 *     desc   : CoverViewLayout 的布局方式统一管理者
 * </pre>
 */
abstract class CoverLayoutManager {
    companion object {
        const val START_LEFT = 0
        const val START_RIGHT = 1
        const val START_TOP = 2
        const val START_BOTTOM = 3
    }

    // 头结点(双链表结构)
    val nodeHead: CoverNode by lazy(LazyThreadSafetyMode.NONE) {
        CoverNode()
    }

    private var width = 0
    private var height = 0

    fun measureCoverLayout(container: ViewGroup, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
        width = View.MeasureSpec.getSize(widthMeasureSpec)
        height = View.MeasureSpec.getSize(heightMeasureSpec)


        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.AT_MOST || View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            // 1. 先整理所有结点的关系
            getCoverNodesLinkList(container)


            // 2. 根据整理的结点关系，进行整体测量，确定 ViewGroup 的宽高
            val measureAllPair = measureCoverTotal(container, widthMeasureSpec, heightMeasureSpec)

            if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.AT_MOST) {
                width = measureAllPair.first
            }

            if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
                height = measureAllPair.second
            }
        }

        return Pair(width, height)
    }


    /**
     * 绘制
     * 1. 先不考虑viewGroup的margin
     * 2. 不考虑层级关系
     * 3. 使用链表结构进行布局
     */
    fun onLayoutCoverLayout(container: ViewGroup) {
        travelLayoutChild(nodeHead.nextNodes)
    }

    private fun travelLayoutChild(list: MutableList<CoverNode>) {
        list.forEach { itemNode ->
            itemNode.view?.layout(itemNode.lLeft, itemNode.lTop, itemNode.lRight, itemNode.lBottom)
            travelLayoutChild(itemNode.nextNodes)
        }
    }


    // 获取所有结点的关系，形成链表 (尝试能够定位到所有子view的位置)
    private fun getCoverNodesLinkList(container: ViewGroup) {
        // 1. 获取到所有的 view 集合
        val listTemp = mutableListOf<CoverNode>()
        container.forEach { child ->

            // fixme : 如果 params 不是 CoverLayoutParams 类型，是否做异常处理
            val params = child.layoutParams as? CoverViewLayout.LayoutParams
            params ?: return

            // 临时集合
            listTemp.add(
                CoverNode().apply {
                    view = child
                    layer = params.coverRelativeLayer
                    coverRelativeId = params.coverRelativeId
                    coverIsRelative = params.coverRelativeId != 0
                    coverMarginRelative = params.coverMarginRelative
                    coverCross = params.coverRelativeCross
                }
            )
        }

        // 重置后续结点集合
        nodeHead.nextNodes = mutableListOf()

        // 2. 处理所有 view 的链表关系
        recursionLinkRelation(listTemp, nodeHead)
    }


    // 3. 递归处理
    private fun recursionLinkRelation(lists: MutableList<CoverNode>, preCoverNode: CoverNode) {
        for (child in lists) {
            // 判断 view 的依赖id是否与上一个view的id相同，相同即存在依赖关系
            if (child.coverRelativeId == preCoverNode.view?.id ?: 0) {
                // 加入依赖 view 的集合 ，并删除当前元素，继续以当前view，查找
                preCoverNode.nextNodes.add(child)
                child.preNode = preCoverNode
                // 从集合中删除元素 child
                // todo : fix (后续迭代过程中删除元素，优化速度) lists.remove(child)
                // 继续以 child 作为父结点，查找依赖 child 的后续结点
                recursionLinkRelation(lists, child)
            }
        }
    }

    abstract fun measureCoverTotal(container: ViewGroup, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int>
}