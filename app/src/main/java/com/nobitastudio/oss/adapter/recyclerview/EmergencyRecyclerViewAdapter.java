package com.nobitastudio.oss.adapter.recyclerview;

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
 * @date 2019/03/23 12:09
 * @description 这是类描述
 */
// 急诊记录
public class EmergencyRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public EmergencyRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_emergency;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.emergency_linearLayout));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
