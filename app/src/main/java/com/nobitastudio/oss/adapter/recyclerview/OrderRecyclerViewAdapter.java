package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.model.enumeration.PaymentChannel;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:14
 * @description 这是类描述
 */
public class OrderRecyclerViewAdapter extends BaseRecyclerViewAdapter<OSSOrder> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public OrderRecyclerViewAdapter(Context ctx, List<OSSOrder> list) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_order;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, OSSOrder item) {
        mQMUILinearLayoutHelper.init(holder.getView(R.id.order_linearLayout));
        holder.getTextView(R.id.order_id_textview).setText(item.getId());
        holder.getTextView(R.id.pay_state_textview).setText(OrderState.getChineseMean(item.getState()));
        holder.getTextView(R.id.order_cost_textview).setText(item.getCost().toString() + "元");
        holder.getTextView(R.id.create_time_textview).setText(DateUtil.convertToStandardDateTime(item.getCreateTime()));
        if (item.getPayTime() != null) {
            holder.getTextView(R.id.pay_time_textview).setText(DateUtil.convertToStandardDateTime(item.getPayTime()));
        } else if (item.getCancelTime() != null) {
            holder.getTextView(R.id.pay_time_textview).setText(DateUtil.convertToStandardDateTime(item.getCancelTime()));
        } else {
            holder.getTextView(R.id.pay_time_textview).setText("待支付");
        }
        holder.getTextView(R.id.pay_channel_textview).setText(PaymentChannel.getChineseMean(item.getPaymentChannel()));
    }
}