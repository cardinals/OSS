package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.Drug;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/04/25 22:09
 * @description 这是类描述
 */
public class OperationItemRecyclerViewAdapter extends BaseRecyclerViewAdapter<Drug> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public OperationItemRecyclerViewAdapter(Context ctx, List<Drug> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_drug;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Drug item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.drug_linearLayout));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
