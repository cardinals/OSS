package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.Drug;
import com.nobitastudio.oss.model.vo.DrugAndCount;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 22:09
 * @description 这是类描述
 */
public class DrugRecyclerViewAdapter extends BaseRecyclerViewAdapter<DrugAndCount> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public DrugRecyclerViewAdapter(Context ctx, List<DrugAndCount> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_drug;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, DrugAndCount item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.drug_linearLayout));
        holder.getTextView(R.id.drug_name_textview).setText(item.getDrug().getName());
        holder.getTextView(R.id.drug_count_textview).setText(item.getCount().toString());
//        holder.getTextView(R.id.drug_count_unit_textview).setText("常规");
        holder.getTextView(R.id.drug_cost_textview).setText(item.getDrug().getPrice() + "元");
        holder.getTextView(R.id.drug_cost_all_textview).setText(item.getCount() * item.getDrug().getPrice() + "元");
    }
}
