package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.model.entity.Doctor;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:10
 * @description 这是类描述
 */
public class CollectionDoctorRecyclerViewAdapter extends BaseRecyclerViewAdapter<Doctor> {

    TipDialogHelper mTipDialogHelper;
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public CollectionDoctorRecyclerViewAdapter(Context ctx, List<Doctor> list) {
        super(ctx, list);
        mTipDialogHelper = new TipDialogHelper(ctx);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_collection_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Doctor item) {
        ImageView mDoctorImageView = holder.getImageView(R.id.doctor_imageview);
        TextView mDoctorNameTextView = holder.getTextView(R.id.doctor_name_textview);
        TextView mDoctorLevelTextView = holder.getTextView(R.id.doctor_level_textView);
        TextView mDoctorDepartmentTextView = holder.getTextView(R.id.deparment_textView);
        TextView mSubMajorTextView = holder.getTextView(R.id.submajor_textView);
        TextView mSpecialityTextView = holder.getTextView(R.id.speciality_textview);
        ImageView mCollectDoctorImageView = holder.getImageView(R.id.collect_doctor_imageview);
        mQMUILinearLayoutHelper.init(holder.getView(R.id.doctor_collect_linearLayout));
        Glide.with(mContext).load(R.drawable.bg_hospital_trademark).into(mDoctorImageView);
        mDoctorNameTextView.setText("名字111");
        mDoctorLevelTextView.setText("医生水平11");
        mDoctorDepartmentTextView.setText("科室信息1111");
        mSubMajorTextView.setText("亚专业11");
        mSpecialityTextView.setText("擅长111");
        mCollectDoctorImageView.setOnClickListener(view -> {
            Glide.with(mContext).load(R.drawable.ic_heart_white).into(mCollectDoctorImageView);
            mTipDialogHelper.showSuccessTipDialog("您已取消收藏", mDoctorImageView);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
