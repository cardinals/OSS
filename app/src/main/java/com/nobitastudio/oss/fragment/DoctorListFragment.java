package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.Doctor;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DoctorListFragment extends StandardWithTobBarLayoutFragment {

    public class DoctorRecycleViewAdapter extends BaseRecyclerViewAdapter<Doctor> {

        public DoctorRecycleViewAdapter(Context ctx, List<Doctor> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_doctor;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Doctor doctor) {
            QMUILinearLayout linearLayout = (QMUILinearLayout) holder.getView(R.id.doctor_linearLayout);
            ImageView imageView = holder.getImageView(R.id.doctor_imageView);
            TextView doctorNameTextView = holder.getTextView(R.id.doctor_name_textview);
            TextView doctorLevelTextView = holder.getTextView(R.id.doctor_level_textView);
            TextView doctorDepartmentTextView = holder.getTextView(R.id.deparment_textView);
            TextView subMajorTextView = holder.getTextView(R.id.submajor_textView);
            TextView specialityTextView = holder.getTextView(R.id.speciality_textView);
            Button button = holder.getButton(R.id.roundButton);
            linearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
            Glide.with(DoctorListFragment.this).load(R.drawable.bg_hospital_trademark).into(imageView);
            doctorNameTextView.setText("名字111");
            doctorLevelTextView.setText("医生水平11");
            doctorDepartmentTextView.setText("科室信息1111");
            subMajorTextView.setText("亚专业11");
            specialityTextView.setText("擅长111");
            button.setText("有号");
        }
    }

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private float mShadowAlpha = 1.0f;
    private int mShadowElevationDp = 10;
    private int mRadius = 15;

    private void initRecycleView() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        DoctorRecycleViewAdapter adapter = new DoctorRecycleViewAdapter(getContext(), doctors);
        adapter.setOnItemClickListener((view, pos) -> startFragment(new DoctorDetailFragment()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("医生列表");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doctor_list;
    }

    @Override
    protected void initLastCustom() {
        initRecycleView();
    }

}
