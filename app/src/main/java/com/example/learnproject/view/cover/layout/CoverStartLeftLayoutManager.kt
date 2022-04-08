package com.example.learnproject.view.cover.layout

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.example.learnproject.view.cover.CoverNode
import com.example.learnproject.view.cover.view.CoverViewLayout
import org.apache.http.params.CoreConnectionPNames

/**
 * <pre>
 *     @author : hpf
 *     time   : 2022/04/07
 *     desc   : 从屏幕左边开始布局
 * </pre>
 */
class CoverStartLeftLayoutManager : CoverLayoutManager() {
    /**
     * 测量容器 (这里简单处理，测试子view的时候，不考虑margin)
     * 1. 首先需要对子 view 进行测量，这里需要注意，以 start_left 布局举例，所有结点不考虑 margin_right
     *      * 以 viewGroup 为基准的结点，需要考虑 margin_left
     *      * 有依赖的view的结点，不考虑 margin_left,也不考虑 margin_right
     *      * 原因
     *          * 以 viewGroup 为基准的结点，如果是 match_parent，这时候有margin_left的情况下，不能紧贴ViewGroup左边缘
     */
    private var maxViewGroupHeight = 0      // 记录 viewGroup 的最大高度
    private var maxViewGroupWidth = 0       // 记录 ViewGroup 的最大宽度
    override fun measureCoverTotal(container: ViewGroup, widthMeasureSpec: Int, heightMeasureSpec: Int): Pair<Int, Int> {
        // 1. 测量所有的子view(这里未考虑子view的margin)
        container.forEach { child ->
            val childWidthMeasureSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, container.paddingLeft + container.paddingRight, child.layoutParams.width)
            val childHeightMeasureSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, container.paddingTop + container.paddingBottom, child.layoutParams.height)
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        }

        /**
         * todo : 布局以及绘制的时候，如何考虑 padding
         * 1. 若是父容器包裹,则计算父容器宽高的时候，考虑进去
         * 2. 若是父容器固定，则绘制的时候，不要超过边界
         * 3. 考虑子view的margin的时候，需要考虑 padding
         */

        /**
         * 2. 计算 viewGroup 的宽高,最好计算出子 view 的坐标点
         * 1. 计算最大高度 : 计算出每一个 view 的最大高度，就可以得出 ViewGroup的最大高度
         * 2. 计算最大宽度 : 需要计算出每条路径的最大宽度，做一个比较(可以直接利用最后一个结点的位置做一个统计)  fixme : 是否可以优化
         */

        // 先将left和top计算进来
        nodeHead.lLeft = container.paddingLeft
        nodeHead.lTop = container.paddingTop

        // 重置ViewGroup的宽高
        maxViewGroupWidth = container.paddingLeft + container.paddingRight
        maxViewGroupHeight = container.paddingTop + container.paddingBottom

        // 遍历结点 (计算 ViewGroup的最大宽度和最大高度)
        travelMeasureChild(nodeHead.nextNodes, nodeHead)

        maxViewGroupWidth = Math.max(maxViewGroupWidth + container.paddingRight, container.paddingLeft + container.paddingRight)
        maxViewGroupHeight = Math.max(maxViewGroupHeight + container.paddingBottom, container.paddingTop + container.paddingBottom)

        return Pair(maxViewGroupWidth, maxViewGroupHeight)
    }

    // 计算子view的left和top位置
    private fun travelMeasureChild(list: MutableList<CoverNode>, preNode: CoverNode) {
        list.forEach { item ->
            val params = item.view?.layoutParams as? CoverViewLayout.CoverLayoutParams ?: return

            // 1. 没有依赖的id的情况下，margin_left和margin_top生效，且基准都是viewGroup
            if (item.coverRelativeId == 0) {
                item.lLeft = params.leftMargin + preNode.lLeft
                item.lTop = params.topMargin + preNode.lTop
            }

            /**
             * 2. 有依赖的情况下，需要细分
             *  * marginTop
             *      ** cover_margin_relative : true -- 考虑子view
             *      ** cover_margin_relative : false -- 考虑viewGroup
             *
             *   * marginLeft
             *      ** cover_cross 生效,不考虑
             *      ** cover_cross 不生效，考虑子view
             */

            if (item.coverRelativeId != 0) {
                // 计算left
                if (item.coverCross != -1) {
                    item.lLeft = preNode.lLeft + (preNode.view?.measuredWidth ?: 0) - item.coverCross       // 减去交叉部分
                } else {
                    item.lLeft = preNode.lLeft + (preNode.view?.measuredWidth ?: 0) + params.leftMargin     // 计算上当前view的margin
                }

                // 计算top
                if (item.coverMarginRelative) {      // 以子view为基准的布局
                    var centerTop = preNode.lTop + ((preNode.view?.measuredHeight ?: 0) - (item.view?.measuredHeight ?: 0)) / 2     // 中线
                    when {
                        params.topMargin > 0 -> {
                            centerTop += params.topMargin
                        }
                        params.bottomMargin > 0 -> {
                            centerTop -= params.topMargin
                        }
                        else -> {
                            item.lTop = centerTop    // fixme : 误差
                        }
                    }
                } else {                             // 以viewGroup为基准的布局
                    item.lTop = params.topMargin + preNode.lTop
                }

                // 计算right和bottom
                item.lRight = item.lLeft + (item.view?.measuredWidth ?: 0)
                item.lBottom = item.lTop + (item.view?.measuredHeight ?: 0)
            }

            // 计算 viewGroup 的最大高度
            val itemHeight = item.lTop + (item.view?.measuredHeight ?: 0)     // 此处是没有考虑 viewGroup 的 paddingBottom 的

            if (itemHeight > maxViewGroupHeight) {
                maxViewGroupHeight = itemHeight
            }

            travelMeasureChild(item.nextNodes, item)
        }


        // 计算 viewGroup 的最大宽度
        if (list.isNullOrEmpty()) {
            val itemWidth = preNode.lLeft + (preNode.view?.measuredWidth ?: 0)
            if (itemWidth > maxViewGroupWidth) {
                maxViewGroupWidth = itemWidth
            }
        }

    }

}