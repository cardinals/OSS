package com.nobitastudio.oss.adapter.recyclerview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.fragment.home.DepartmentFragment;
import com.nobitastudio.oss.model.entity.Department;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 12:01
 * @description 这是类描述
 */
public class DepartmentRecyclerViewAdapter extends BaseRecyclerViewAdapter<Department> {

    public DepartmentRecyclerViewAdapter(Context ctx, List<Department> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_item_department;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Department item) {
        holder.setText(R.id.textview, item.getName());
        Glide.with(mContext).load(getImageDrawableId(item)).into(holder.getImageView(R.id.imageview));
    }

    private int getImageDrawableId(Department item) {
        return ConstantContainer.getDepartmentMipmap().getOrDefault(item.getName(), R.mipmap.ic_transparent);
    }
}
