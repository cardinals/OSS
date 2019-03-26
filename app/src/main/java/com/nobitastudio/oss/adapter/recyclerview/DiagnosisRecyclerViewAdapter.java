package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.OSSOrder;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:16
 * @description 这是类描述
 */
public class DiagnosisRecyclerViewAdapter extends BaseRecyclerViewAdapter<OSSOrder> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public DiagnosisRecyclerViewAdapter(Context ctx, List<OSSOrder> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_diagnosis;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, OSSOrder item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.diagnosis_linearLayout));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
