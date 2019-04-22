package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.dto.RegistrationAll;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:16
 * @description 就诊描述
 */
public class DiagnosisRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationAll> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public DiagnosisRecyclerViewAdapter(Context ctx, List<RegistrationAll> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_diagnosis;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, RegistrationAll item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.diagnosis_linearLayout));
        switch(item.getOssOrder().getItemType()) {
            case REGISTER:
                holder.getTextView(R.id.type_textview).setText("挂号单项");
                break;
            case CHECK:
                holder.getTextView(R.id.type_textview).setText("检查单项");
                break;
            case OPERATION:
                holder.getTextView(R.id.type_textview).setText("手术单项");
                break;
        }
        holder.getTextView(R.id.department_textview).setText(item.getDepartment().getName());
        holder.getTextView(R.id.doctor_name_textview).setText(item.getDoctor().getName());
        holder.getTextView(R.id.diagnosis_time_textview).setText(DateUtil.convertToStandardDateTime(item.getVisit().getDiagnosisTime()));
        holder.getTextView(R.id.diagnosis_room_textview).setText(item.getDiagnosisRoom().getName());
        holder.getTextView(R.id.medical_card_ownername_textview).setText(item.getMedicalCard().getOwnerName());
        holder.getTextView(R.id.medical_card_id_textview).setText(item.getMedicalCard().getId());
    }
}
