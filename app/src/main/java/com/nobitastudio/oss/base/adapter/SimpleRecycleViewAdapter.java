package com.nobitastudio.oss.base.adapter;

import android.content.Context;

import com.nobitastudio.oss.R;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/30 15:33
 * @description 该RecycleViewAdapter 是最简单的基础布局，表现为和Listview相同，以为listview 与 refreshView 存在冲突,使用recycleView进行替代.
 */
public abstract class SimpleRecycleViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    private final static int SIMPLE_LAYOUT = R.layout.recyclerview_item_simple;
    private final static int SIMPLE_LAYOUT_TEXTVIEW_ID = R.id.text;

    public SimpleRecycleViewAdapter(Context ctx, List<T> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return SIMPLE_LAYOUT;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, T item) {
        holder.setText(SIMPLE_LAYOUT_TEXTVIEW_ID, display(position, item));
    }

    /**
     * 展示信息使用
     * @param position
     * @param item
     * @return
     */
    public abstract String display(int position, T item);
}
