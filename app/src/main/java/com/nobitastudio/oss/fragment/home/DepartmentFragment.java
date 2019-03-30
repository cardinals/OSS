package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DepartmentRecyclerViewAdapter;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 展示科室详情.
 */
public class DepartmentFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.emptyview)
    QMUIEmptyView mEmptyView;

    List<Department> mDepartments;
    DepartmentRecyclerViewAdapter adapter;

    private void initRecyclerView() {
        mDepartments = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        adapter = new DepartmentRecyclerViewAdapter(getContext(), mDepartments);
        adapter.setOnItemClickListener((itemView, pos) -> {
            NormalContainer.put(NormalContainer.SELECTED_DEPARTMENT, mDepartments.get(pos));
            startFragment(new DoctorListFragment());
        });
        adapter.setOnItemLongClickListener((itemView, pos) -> {
            Department department = mDepartments.get(pos);
            showLongMessageDialog(department.getName(),generateDepartmentInfo(department),
                    null,null,
                    "知道了",(dialog,index) -> dialog.dismiss());
        });
        mRecyclerView.setAdapter(adapter);
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
                "介绍：" + department.getIntroduction();
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return view -> {
            showLoadingEmptyView("正在加载",mEmptyView);
            refresh(Boolean.FALSE);
        };
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
    protected void refresh(Boolean isCancelPull) {
        getAsyn(Arrays.asList("department"), null, new ReflectStrategy<>(new TypeReference<List<Department>>() {
                }), new OkHttpUtil.SuccessHandler<List<Department>>() {
                    @Override
                    public void handle(List<Department> departments) {
                        List<Department> mContainICDepartments = departments.stream().filter(ConstantContainer.CONTAIN_IC_DEPARTMENTS::contains)
                                .collect(Collectors.toList()); // 包含图标的department
                        List<Department> mNoContainICDepartments = departments.stream().filter(item -> !ConstantContainer.CONTAIN_IC_DEPARTMENTS.contains(item))
                                .collect(Collectors.toList()); // 不包含图标的department
                        mDepartments.addAll(mContainICDepartments);
                        mDepartments.addAll(mNoContainICDepartments);
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                        mEmptyView.hide();
                    }
                },
                new OkHttpUtil.FailHandler<List<Department>>() {
                    @Override
                    public void handle(ServiceResult<List<Department>> serviceResult) {
                        showInfoTipDialog(ErrorCode.get(serviceResult.getErrorCode()));
                        showLoadingFailEmptyView("加载失败", "点击重试", mEmptyView);
                    }
                }
        );
    }

    @Override
    protected void initLastCustom() {
        showLoadingEmptyView("正在加载", mEmptyView); //显示
        initRecyclerView();
    }

}
