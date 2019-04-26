package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.Drug;
import com.nobitastudio.oss.model.vo.OperationAndCount;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 22:09
 * @description 这是类描述
 */
public class OperationItemRecyclerViewAdapter extends BaseRecyclerViewAdapter<OperationAndCount> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public OperationItemRecyclerViewAdapter(Context ctx, List<OperationAndCount> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_operation;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, OperationAndCount item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.operation_linearLayout));
        holder.getTextView(R.id.operation_item_name_textview).setText(item.getOperationItem().getName());
        holder.getTextView(R.id.operation_item_unit_textview).setText("次");
        holder.getTextView(R.id.operation_item_count_textview).setText(item.getCount().toString());
//        holder.getTextView(R.id.order_create_time_textview).setText("不详");
//        holder.getTextView(R.id.drug_count_unit_textview).setText("常规");
        holder.getTextView(R.id.operation_item_cost_textview).setText(item.getOperationItem().getPrice() + "元");
        holder.getTextView(R.id.operation_item_cost_all_textview).setText(item.getCount() * item.getOperationItem().getPrice() + "元");
    }
}
