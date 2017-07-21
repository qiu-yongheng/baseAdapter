package com.zhy.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ItemViewDelegateManager;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author 邱永恒
 * @time 16/4/9  14:03
 * @desc 支持显示多种item类型的adapter
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    /**
     * item类型管理类
     */
    protected ItemViewDelegateManager mItemViewDelegateManager;

    /**
     * 点击监听
     */
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 获取item的type
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        /** 判断map集合中, 是否有添加定义的item类型 */
        if (!useItemViewDelegateManager()) {
            /** 没有, 让父类自己去判断item类型 */
            return super.getItemViewType(position);
        }

        /** 给子类判断item类型 */
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** 根据viewType, 获取view的布局ID */
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);

        /** 创建ViewHolder */
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);

        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    /**
     *
     * @param holder
     * @param itemView
     */
    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    /**
     * 给控件绑定数据
     * @param holder
     * @param t
     */
    public void convert(ViewHolder holder, T t) {
        /** 给子类实现, 数据绑定 */
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    /**
     * 传入控件类型, 判断是否可用响应点击事件
     * @param viewType
     * @return
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * 设置点击事件
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }

        /**
         * 点击
         */
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        /**
         * 长按
         */
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    /**
     * 获取数据长度
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 获取数据
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 添加item类型案例
     * @param itemViewDelegate item类型样式布局案例
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 添加item类型案例
     * @param viewType 用户自定义的viewType描述
     * @param itemViewDelegate item类型样式布局案例
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * 判断map集合中, 是否保存有item类型
     * @return
     */
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * item点击监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    /**
     * 设置点击监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
