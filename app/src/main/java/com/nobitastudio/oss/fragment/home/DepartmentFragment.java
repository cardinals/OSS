package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DepartmentRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.Department;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DepartmentFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.emptyview)
    QMUIEmptyView mEmptyView;

    List<Department> mDepartments;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private void initRecyclerView() {
        mDepartments = new ArrayList<>();
        mDepartments.add(new Department("脑科"));
        mDepartments.add(new Department("内分泌科"));
        mDepartments.add(new Department("肿瘤科"));
        mDepartments.add(new Department("儿科"));
        mDepartments.add(new Department("泌尿科"));
        mDepartments.add(new Department("内科"));
        mDepartments.add(new Department("外科"));
        mDepartments.add(new Department("生殖科"));
        mDepartments.add(new Department("中医科"));
        mDepartments.add(new Department("康复科"));
        mDepartments.add(new Department("病理科"));
        mDepartments.add(new Department("男科"));
        mDepartments.add(new Department("其他科"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        DepartmentRecyclerViewAdapter adapter = new DepartmentRecyclerViewAdapter(getContext(), mDepartments);
        adapter.setOnItemClickListener((itemView, pos) -> startFragment(new DoctorListFragment()));
        adapter.setOnItemLongClickListener((itemView, pos) -> {
            Department department = mDepartments.get(pos);
            new QMUIDialog.MessageDialogBuilder(getContext())
                    .setTitle(department.getName())
                    .setMessage(generateDepartmentInfo(department))
                    .addAction("关闭", (dialog, index) -> dialog.dismiss())
                    .create(mCurrentDialogStyle).show();
        });
        mRecyclerView.setAdapter(adapter);
        mTopBar.postDelayed(() -> closeLoadingEmptyView(mEmptyView), 1000);
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
        return view -> showLoadingEmptyView(null, mEmptyView);
    }

    @Override
    protected String getTopBarTitle() {
        return "科室列表";
    }

    @Override
    protected void initTopBarLast() {
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
