package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.entity.CheckItem;
import com.nobitastudio.oss.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:04
 * @description 这是类描述
 */
public class ExpressRecyclerViewAdapter extends BaseRecyclerViewAdapter<ElectronicCaseDTO> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public ExpressRecyclerViewAdapter(Context ctx, List<ElectronicCaseDTO> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_express;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, ElectronicCaseDTO item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.express_linearLayout));
        holder.getTextView(R.id.medical_card_ownername_textview).setText(item.getRegistrationAll().getMedicalCard().getOwnerName());
        holder.getTextView(R.id.department_textview).setText(item.getRegistrationAll().getDepartment().getName());
        holder.getTextView(R.id.doctor_name_textview).setText(item.getRegistrationAll().getDoctor().getName());
        holder.getTextView(R.id.create_time_textview).setText(DateUtil.convertToStandardDateTime(item.getOssOrder().getCreateTime()));
        holder.getTextView(R.id.check_items_textview).setText(generateCheckItems(item));
    }

    private String generateCheckItems(ElectronicCaseDTO item) {
        StringBuilder builder = new StringBuilder();
        for (CheckItem checkItem : item.getCheckItems()) {
            builder.append(checkItem.getName() + ",");
        }
        return builder.substring(0,builder.length() - 1);
    }
}
