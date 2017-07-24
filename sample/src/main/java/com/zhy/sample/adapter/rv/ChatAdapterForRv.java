package com.zhy.sample.adapter.rv;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.sample.bean.ChatMessage;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage> {
    public ChatAdapterForRv(Context context, List<ChatMessage> datas) {
        super(context, datas);

        // 添加item类型, 数据绑定, view初始化已在内部处理
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }
}
