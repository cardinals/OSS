package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 11:59
 * @description 这是类描述
 */

// 名师讲堂
public class DoctorLectureRecyclerViewAdapter extends BaseRecyclerViewAdapter<HealthArticle> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public DoctorLectureRecyclerViewAdapter(Context ctx, List<HealthArticle> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_doctor_lecture;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
        mQMUILinearLayoutHelper.init((holder.getView(R.id.doctor_lecture_linearlayout)));
        Glide.with(mContext).load(mData.get(position).getIconUrl()).into(holder.getImageView(R.id.cover_imageview));
        holder.setText(R.id.title_textview, mData.get(position).getTitle());
        holder.setText(R.id.publish_man_textview, mData.get(position).getPublishMan());
        holder.setText(R.id.label_textview, mData.get(position).getLabel());
        holder.setText(R.id.publish_time_textview, DateUtil.formatLocalDateTimeToStandardString(mData.get(position).getPublishTime()));
        holder.setText(R.id.video_time_textview,mData.get(position).getOtherData());
    }
}
