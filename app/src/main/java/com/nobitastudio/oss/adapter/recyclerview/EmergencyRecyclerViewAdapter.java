package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:09
 * @description 这是类描述
 */
// 急诊记录
public class EmergencyRecyclerViewAdapter extends BaseRecyclerViewAdapter<ElectronicCaseDTO> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public EmergencyRecyclerViewAdapter(Context ctx, List<ElectronicCaseDTO> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_emergency;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, ElectronicCaseDTO item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.emergency_linearLayout));
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + item.getRegistrationAll().getDoctor().getIconUrl())
                .into(holder.getImageView(R.id.doctor_imageview));
        holder.getTextView(R.id.doctor_name_textview).setText(item.getRegistrationAll().getDoctor().getName());
        holder.getTextView(R.id.doctor_level_textview).setText(DoctorLevel.translateToString(item.getRegistrationAll().getDoctor().getLevel()));
        holder.getTextView(R.id.department_textview).setText(item.getRegistrationAll().getDepartment().getName());
        holder.getTextView(R.id.speciality_textview).setText(item.getRegistrationAll().getDoctor().getSubMajor());
        holder.getTextView(R.id.diagnosis_time_textview).setText(DateUtil.convertToStandardDateTime(item.getElectronicCase().getDiagnosisTime()));
    }
}
