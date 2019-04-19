package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:10
 * @description 这是类描述
 */
public class CollectionDoctorRecyclerViewAdapter extends BaseRecyclerViewAdapter<Doctor> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public CollectionDoctorRecyclerViewAdapter(Context ctx, List<Doctor> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_collection_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Doctor doctor) {
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + doctor.getIconUrl()).into(holder.getImageView(R.id.doctor_imageview));
        holder.getTextView(R.id.doctor_name_textview).setText(doctor.getName());
        holder.getTextView(R.id.doctor_level_textView).setText(DoctorLevel.translateToString(doctor.getLevel()));
//        holder.getTextView(R.id.deparment_textview).setText(mSelectedDepartment.getName());
        holder.getTextView(R.id.submajor_textView).setText(doctor.getSubMajor());
        holder.getTextView(R.id.speciality_textview).setText(doctor.getSpecialty());
        mQMUILinearLayoutHelper.init(holder.getView(R.id.doctor_collect_linearLayout));
//        holder.getView()mCollectDoctorImageView.setOnClickListener(view -> {
//            Glide.with(mContext).load(R.drawable.ic_heart_white).into(mCollectDoctorImageView);
//            mTipDialogHelper.showSuccessTipDialog("您已取消收藏", mDoctorImageView);
//        });
    }

}
