package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.MedicalCard;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:05
 * @description 这是类描述
 */
public class MedicalCardItemAdapter extends BaseRecyclerViewAdapter<MedicalCard> {

    public MedicalCardItemAdapter(Context ctx, List<MedicalCard> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_medical_card;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, MedicalCard item) {
        holder.getTextView(R.id.owner_name_textview).setText(item.getOwnerName());
        holder.getTextView(R.id.medical_card_no_textview).setText(item.getId());
    }
}