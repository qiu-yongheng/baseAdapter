package com.zhy.adapter.recyclerview.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * @author 邱永恒
 * @time 16/6/28  13:51
 * @desc 装饰者模式工具类, 设置item的跨度
 */
public class WrapperUtils {
    /**
     * 接口, 给子类设置控件的跨度
     */
    public interface SpanSizeCallback {
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
    }

    /**
     * 如果是GridLayoutManager, 设置item跨度
     * @param innerAdapter
     * @param recyclerView
     * @param callback
     */
    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, final SpanSizeCallback callback) {
        innerAdapter.onAttachedToRecyclerView(recyclerView);

        /** 获取recyclerView的布局管理器 */
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        /** 如果是GridLayoutManager, 修改item占长宽的比例 */
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            /** 设置如果是GridLayoutManager, item跨度 */
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    /** span size表示一个item的跨度，跨度了多少个span */
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });

            /** 设置item跨度 */
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    /**
     * 瀑布流专用, 设置item跨度
     * @param holder
     */
    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {

            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }
}
