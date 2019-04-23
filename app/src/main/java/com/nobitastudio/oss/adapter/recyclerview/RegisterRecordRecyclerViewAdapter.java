package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.dto.RegistrationAll;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:15
 * @description 这是类描述
 */
public class RegisterRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationAll> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public RegisterRecordRecyclerViewAdapter(Context ctx, List<RegistrationAll> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_register_history;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, RegistrationAll item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.register_record_linearLayout));
        holder.getTextView(R.id.register_record_no_textview).setText(item.getRegistrationRecord().getId());
        holder.getTextView(R.id.pay_state_textview).setText(OrderState.getChineseMean(item.getOssOrder().getState()));
        holder.getTextView(R.id.diagnosis_no_textview).setText(item.getRegistrationRecord().getDiagnosisNo() + "号");
        holder.getTextView(R.id.register_cost_textView).setText(item.getVisit().getCost() + "元");
        holder.getTextView(R.id.register_date_textview).setText(DateUtil.convertToStandardDateTime(item.getRegistrationRecord().getCreateTime()));
    }
}