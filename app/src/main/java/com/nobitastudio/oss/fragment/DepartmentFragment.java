package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.Department;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DepartmentFragment extends StandardWithTobBarLayoutFragment {

    class DepartmentRecyclerViewAdapter extends BaseRecyclerViewAdapter<Department> {

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
            int imgResId = getImageDrawableId(item);
            if (imgResId != 0) {
                Glide.with(DepartmentFragment.this).load(getImageDrawableId(item)).into(holder.getImageView(R.id.imageview));
            }
        }
    }

    private int getImageDrawableId(Department item) {
        int imgResId;
        imgResId = item.hashCode() % 2 == 0 ? R.mipmap.ic_bone : R.mipmap.ic_brain;
//        switch (item.getName()) {
//            case "脑科":
//                imgResId = R.mipmap.ic_brain;
//                break;
//            case "骨科":
//                imgResId = R.mipmap.ic_bone;
//                break;
//            default:
//                imgResId = 0;
//                break;
//        }
        return imgResId;
    }

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    List<Department> departments;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private void initRecyclerView() {
        departments = new ArrayList<>();
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        departments.add(new Department());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        DepartmentRecyclerViewAdapter adapter = new DepartmentRecyclerViewAdapter(getContext(), departments);
        adapter.setOnItemClickListener((itemView, pos) -> {
            startFragment(new DoctorListFragment());
        });
        adapter.setOnItemLongClickListener((itemView, pos) -> {
            Department department = departments.get(pos);
            new QMUIDialog.MessageDialogBuilder(getContext())
                    .setTitle(department.getName())
                    .setMessage(generateDepartmentInfo(department))
                    .addAction("关闭", (dialog, index) -> dialog.dismiss())
                    .create(mCurrentDialogStyle).show();
        });
        mRecyclerView.setAdapter(adapter);
        mTopBar.postDelayed(() -> {
            showLoadingFailEmptyView("加载失败", "点击重试");
        }, 1500);

        mTopBar.postDelayed(this::closeLoadingEmptyView, 5500);
    }

    /**
     * 在科室列表生成科室基本信息
     *
     * @param department
     * @return
     */
    private String generateDepartmentInfo(Department department) {
        return "楼层:" + department.getFloor() + "\n" +
                "区域：" + department.getArea().name() + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "介绍：介绍：介绍：介绍：介绍：介" + "\n" +
                "绍：介绍：介绍：" + department.getIntroduction();
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                // 完成后调用finishRefresh关闭
                mPullRefreshLayout.postDelayed(() -> {
                    mPullRefreshLayout.finishRefresh();
                    ToastUtils.showShort("刷新成功");
                }, 2000);
            }
        });
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return view -> showLoadingEmptyView(null);
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("科室列表");
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_setting, R.id.topbar_right_setting_button)
                .setOnClickListener(view -> showInfoTipDialog("长按指定科室可查看详情", 2500l));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_department;
    }

    @Override
    protected void initLastCustom() {
        initRecyclerView();
    }

}
