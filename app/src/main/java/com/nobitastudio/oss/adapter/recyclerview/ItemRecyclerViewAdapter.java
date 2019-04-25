package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.dto.ItemDescription;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:06
 * @description 这是类描述
 */
public class ItemRecyclerViewAdapter extends BaseRecyclerViewAdapter<ItemDescription> {

    public ItemRecyclerViewAdapter(Context ctx, List<ItemDescription> data) {
        super(ctx, data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_info;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, ItemDescription item) {
        holder.getTextView(R.id.item_name).setText(item.getName());
        if (item.getIconRes() != 0) {
            holder.getImageView(R.id.item_icon).setImageResource(item.getIconRes());
        }
    }
}
