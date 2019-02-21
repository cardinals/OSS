package com.nobitastudio.oss.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.SimpleRecycleViewAdapter;
import com.nobitastudio.oss.model.entity.Department;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DepartmentFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    List<Department> departments;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private void initListView() {
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
        SimpleRecycleViewAdapter<Department> adapter = new SimpleRecycleViewAdapter<Department>(getContext(), departments) {
            @Override
            public String display(int position, Department department) {
                return department.toString();
            }
        };
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
        initListView();
    }

}
