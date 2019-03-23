package com.nobitastudio.oss.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:03
 * @description 这是类描述
 */
public class VisitRecycleViewAdapter extends BaseRecyclerViewAdapter<Visit> {
    public VisitRecycleViewAdapter(Context ctx, List<Visit> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_visit;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Visit item) {
        TextView mDateTextView = holder.getTextView(R.id.date_textview);
        TextView mWeekTextView = holder.getTextView(R.id.week_textview);
        TextView mTimeSlotTextView = holder.getTextView(R.id.time_slot_textview);
        TextView mHospitalNameTextView = holder.getTextView(R.id.hospital_name_textview);
        TextView mSubMajorTextView = holder.getTextView(R.id.submajor_textview);
        TextView mAreaTextView = holder.getTextView(R.id.area_textview);
        QMUIRoundButton mGreenRoundButton = (QMUIRoundButton) holder.getView(R.id.green_roundbutton);
        QMUIRoundButton mRedRoundButton = (QMUIRoundButton) holder.getView(R.id.red_roundbutton);

        mDateTextView.setText(DateUtil.formatLocalDateTimeToSimpleString2(item.getDiagnosisTime()));
        mWeekTextView.setText(item.getDiagnosisTime().getDayOfWeek().name());
        mTimeSlotTextView.setText("上午");
        mHospitalNameTextView.setText("石河子大学医学院");
        mSubMajorTextView.setText("这是医生的亚专业");
        mAreaTextView.setText("A区");
        if (position > 3) {
            mGreenRoundButton.setVisibility(View.GONE);
            mRedRoundButton.setVisibility(View.VISIBLE);
        } else {
            mGreenRoundButton.setVisibility(View.VISIBLE);
            mRedRoundButton.setVisibility(View.GONE);
        }
    }
}