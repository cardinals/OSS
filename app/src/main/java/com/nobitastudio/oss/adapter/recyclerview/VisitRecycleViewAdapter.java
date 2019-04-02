package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;
import android.widget.Button;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.NormalContainerHelper;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.model.enumeration.Area;
import com.nobitastudio.oss.util.DateUtil;

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
        holder.getTextView(R.id.date_textview).setText(DateUtil.convertToStandardDate(item.getDiagnosisTime()));
        holder.getTextView(R.id.week_textview).setText(DateUtil.convertDayOfWeekToChinese(item.getDiagnosisTime().getDayOfWeek()));
        // item.getDiagnosisTime().getHour() 返回 0 ~ 23 14点后是下午.
        holder.getTextView(R.id.time_slot_textview).setText(item.getDiagnosisTime().getHour() < 13 ? "上午" : "下午");
        holder.getTextView(R.id.hospital_name_textview).setText(mContext.getString(R.string.hospital_name));
        holder.getTextView(R.id.submajor_textview).setText(NormalContainerHelper.getInstance().getSelectedDoctor().getSubMajor());
        holder.getTextView(R.id.area_textview).setText(Area.convertToChinese(NormalContainerHelper.getInstance().getSelectedDepartment().getArea()));
        initButton(item, holder.getButton(R.id.roundButton));
    }

    // 决定医生是否有号
    private void initButton(Visit visit, Button button) {
        if (visit.getLeftAmount() != 0) {
            button.setText("有号");
            button.setTextColor(mContext.getResources().getColor(R.color.app_color_theme_5, null));
            button.setBackground(mContext.getDrawable(R.drawable.bg_green_radius_button));
        } else {
            button.setText("无号");
            button.setTextColor(mContext.getResources().getColor(R.color.app_color_theme_2, null));
            button.setBackground(mContext.getDrawable(R.drawable.bg_red_radius_button));
        }
    }
}