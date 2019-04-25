package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.model.vo.DoctorAndVisit;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:03
 * @description 这是类描述
 */
public class DoctorRecycleViewAdapter extends BaseRecyclerViewAdapter<DoctorAndVisit> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;
    Department mSelectedDepartment;

    public DoctorRecycleViewAdapter(Context ctx, List<DoctorAndVisit> list, Department mSelectedDepartment) {
        super(ctx, list);
        this.mSelectedDepartment = mSelectedDepartment;
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, DoctorAndVisit doctorAndVisit) {
        Doctor doctor = doctorAndVisit.getDoctor();
        List<Visit> visits = doctorAndVisit.getVisits();
        mQMUILinearLayoutHelper.init(holder.getView(R.id.doctor_linearLayout));
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + doctor.getIconUrl()).into(holder.getImageView(R.id.doctor_imageview));
        holder.getTextView(R.id.doctor_name_textview).setText(doctor.getName());
        holder.getTextView(R.id.doctor_level_textview).setText(DoctorLevel.translateToString(doctor.getLevel()));
        holder.getTextView(R.id.department_textview).setText(mSelectedDepartment.getName());
        holder.getTextView(R.id.submajor_textview).setText(doctor.getSubMajor());
        holder.getTextView(R.id.speciality_textview).setText(doctor.getSpecialty());
        initButton(visits, holder.getButton(R.id.roundButton));
    }

    // 决定医生是否有号
    private void initButton(List<Visit> visits, Button button) {
        boolean canRegister = false;
        for (Visit visit : visits) {
            if (visit.getLeftAmount() != 0) {
                canRegister = true;
                break;
            }
        }
        if (canRegister) {
            button.setText("有号");
            button.setTextColor(mContext.getResources().getColor(R.color.app_color_theme_5, null));
            button.setBackground(mContext.getDrawable(R.drawable.bg_green_radius_button));
        } else {
            button.setText("无号");
            button.setTextColor(mContext.getResources().getColor(R.color.app_color_theme_2, null));
            button.setBackground(mContext.getDrawable(R.drawable.bg_red_radius_button));
        }
    }
}
