package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:04
 * @description 这是类描述
 */
public class ExpressRecyclerViewAdapter extends BaseRecyclerViewAdapter<Object> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public ExpressRecyclerViewAdapter(Context ctx, List<Object> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_express;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Object item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.express_linearLayout));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
