package com.nobitastudio.oss.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 11:58
 * @description 这是类描述
 */
public class HeadlineRecycleViewAdapter extends BaseRecyclerViewAdapter<HealthArticle> {

    public HeadlineRecycleViewAdapter(Context ctx, List<HealthArticle> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_headline;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
        Glide.with(mContext).load(R.drawable.bg_hospital_trademark).into(holder.getImageView(R.id.cover_imageView));
        holder.setText(R.id.title_textView, item.getTitle());
        holder.setText(R.id.label_textview, item.getLabel());
        holder.setText(R.id.publish_time_textView, DateUtil.formatLocalDateTimeToSimpleString(item.getPublishTime()));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
