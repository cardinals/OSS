package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.DialogHelper;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.inter.RecyclerViewClickHandler;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;
import com.nobitastudio.oss.model.dto.DoctorAndDepartment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:10
 * @description 这是类描述
 */
public class CollectionDoctorRecyclerViewAdapter extends BaseRecyclerViewAdapter<DoctorAndDepartment> {

    QMUILinearLayoutHelper mQMUILinearLayoutHelper;
    RecyclerViewClickHandler<DoctorAndDepartment> handler;

    public CollectionDoctorRecyclerViewAdapter(Context ctx, List<DoctorAndDepartment> list, RecyclerViewClickHandler<DoctorAndDepartment> handler) {
        super(ctx, list);
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(ctx);
        this.handler = handler;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recycleview_item_collection_doctor;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, DoctorAndDepartment doctorAndDepartment) {
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + doctorAndDepartment.getDoctor().getIconUrl()).into(holder.getImageView(R.id.doctor_imageview));
        holder.getTextView(R.id.doctor_name_textview).setText(doctorAndDepartment.getDoctor().getName());
        holder.getTextView(R.id.doctor_level_textView).setText(DoctorLevel.translateToString(doctorAndDepartment.getDoctor().getLevel()));
        holder.getTextView(R.id.deparment_textview).setText(doctorAndDepartment.getDepartment().getName());
        holder.getTextView(R.id.submajor_textView).setText(doctorAndDepartment.getDoctor().getSubMajor());
        holder.getTextView(R.id.speciality_textview).setText(doctorAndDepartment.getDoctor().getSpecialty());
        mQMUILinearLayoutHelper.init(holder.getView(R.id.doctor_collect_linearLayout));
        holder.getImageView(R.id.collect_doctor_imageview).setOnClickListener(view -> handler.handle(doctorAndDepartment));
    }

}
