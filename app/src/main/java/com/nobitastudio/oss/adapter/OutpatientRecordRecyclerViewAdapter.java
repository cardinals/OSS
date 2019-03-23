package com.nobitastudio.oss.adapter;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.RegistrationRecord;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:07
 * @description 门诊记录
 */
public class OutpatientRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public OutpatientRecordRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_outpatient;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.outpatient_linearLayout));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
