package com.zhy.adapter.recyclerview.base;

import android.support.v4.util.SparseArrayCompat;


/**
 * @author 邱永恒
 * @time 16/6/22  13:47
 * @desc item类型管理类
 */
public class ItemViewDelegateManager<T> {
    /**
     * 保存item类型的map集合
     */
    private SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    /**
     * 判断map集合中, 保存有多少个控件
     * @return
     */
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /**
     * 添加item类型案例
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        /** 获取类型样式个数, 作为key */
        int viewType = delegates.size();
        if (delegate != null) {
            /** 将当前类型样式个数, 作为key, 样式模板作为value */
            delegates.put(viewType, delegate);
        }
        return this;
    }

    /**
     * 添加item类型案例, 用户自定义viewType
     * @param viewType
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除item类型 (根据value移除)
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }

        /** 获取value对应的key */
        int indexToRemove = delegates.indexOfValue(delegate);

        /** 如果key存在, 删除 */
        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 移除item类型 (根据key移除)
     * @param itemType
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 提供给adapter调用, 判断当前item类型
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        /** 遍历获取item类型, 判断当前position对应type */
        for (int i = delegatesCount - 1; i >= 0; i--) {
            /** 获取item类型 */
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            /** 给子类实现判断, 子类根据: item对应的数据, position --> 判断item当前类型 */
            if (delegate.isForViewType(item, position)) {
                /** 返回item类型id */
                return delegates.keyAt(i);
            }
        }

        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 绑定数据
     * @param holder holder
     * @param item 数据
     * @param position position
     */
    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {

            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                /** 给子类实现, 数据绑定 */
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 根据viewType, 给viewHolder设置layoutID
     * @param viewType
     * @return
     */
    public ItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    /**
     * 根据viewType, 给viewHolder设置layoutID
     * @param viewType
     * @return
     */
    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    /**
     *
     * @param itemViewDelegate
     * @return
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
