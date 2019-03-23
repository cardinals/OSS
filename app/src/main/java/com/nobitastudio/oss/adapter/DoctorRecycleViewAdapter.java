package com.nobitastudio.oss.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.fragment.home.DoctorListFragment;
import com.nobitastudio.oss.model.entity.Doctor;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:03
 * @description 这是类描述
 */
public class DoctorRecycleViewAdapter extends BaseRecyclerViewAdapter<Doctor> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public DoctorRecycleViewAdapter(Context ctx, List<Doctor> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Doctor doctor) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.doctor_linearLayout));
        ImageView imageView = holder.getImageView(R.id.doctor_imageview);
        TextView doctorNameTextView = holder.getTextView(R.id.doctor_name_textview);
        TextView doctorLevelTextView = holder.getTextView(R.id.doctor_level_textView);
        TextView doctorDepartmentTextView = holder.getTextView(R.id.deparment_textView);
        TextView subMajorTextView = holder.getTextView(R.id.submajor_textView);
        TextView specialityTextView = holder.getTextView(R.id.speciality_textView);
        Button button = holder.getButton(R.id.roundButton);
        Glide.with(mContext).load(R.drawable.bg_hospital_trademark).into(imageView);
        doctorNameTextView.setText("名字111");
        doctorLevelTextView.setText("医生水平11");
        doctorDepartmentTextView.setText("科室信息1111");
        subMajorTextView.setText("亚专业11");
        specialityTextView.setText("擅长111");
        button.setText("有号");
    }
}
