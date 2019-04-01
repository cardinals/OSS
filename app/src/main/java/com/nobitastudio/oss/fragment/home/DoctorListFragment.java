package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DoctorRecycleViewAdapter;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.DoctorAndVisit;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DoctorListFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.emptyview)
    QMUIEmptyView mEmptyView;

    List<DoctorAndVisit> mDoctorAndVisits;
    DoctorRecycleViewAdapter mDoctorRecycleViewAdapter;
    Department mSelectedDepartment;

    private void initRecycleView() {
        mDoctorAndVisits = new ArrayList<>();
        mDoctorRecycleViewAdapter = new DoctorRecycleViewAdapter(getContext(), mDoctorAndVisits, mSelectedDepartment);
        mDoctorRecycleViewAdapter.setOnItemClickListener((view, pos) -> {
            NormalContainer.put(NormalContainer.SELECTED_DOCTOR, mDoctorAndVisits.get(pos).getDoctor());
            NormalContainer.put(NormalContainer.VISITS, mDoctorAndVisits.get(pos).getVisits());
            startFragment(new DoctorDetailFragment());
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mRecyclerView.setAdapter(mDoctorRecycleViewAdapter);
    }

    @Override
    protected String getTopBarTitle() {
        return "医生列表";
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return view -> {
            showLoadingEmptyView("正在加载", mEmptyView);
            refresh(Boolean.FALSE);
        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doctor_list;
    }

    @Override
    protected void refresh(Boolean isCancelPull) {
        getAsyn(Arrays.asList("doctor", "byDepartment", mSelectedDepartment.getId().toString()), null, new ReflectStrategy<>(new TypeReference<List<DoctorAndVisit>>() {
        }), new OkHttpUtil.SuccessHandler<List<DoctorAndVisit>>() {
            @Override
            public void handle(List<DoctorAndVisit> doctorAndVisits) {
                mDoctorAndVisits.clear();
                mDoctorAndVisits.addAll(doctorAndVisits);
                runOnUiThread(() -> mDoctorRecycleViewAdapter.notifyDataSetChanged());
                mEmptyView.hide();
            }
        }, new OkHttpUtil.FailHandler<List<DoctorAndVisit>>() {
            @Override
            public void handle(ServiceResult<List<DoctorAndVisit>> serviceResult) {
                showInfoTipDialog(ErrorCode.get(serviceResult.getErrorCode()));
                showLoadingFailEmptyView("加载失败", "点击重试", mEmptyView);
            }
        });
    }

    @Override
    public OkHttpUtil.ConnectFailHandler getConnectFailHandler() {
        return (call, e) -> {
            showLoadingFailEmptyView("加载失败", "点击重试", mEmptyView);
        };
    }

    @Override
    protected void initLastCustom() {
        initBasic();
        showLoadingEmptyView("正在加载", mEmptyView); //显示
        initRecycleView();
    }

    private void initBasic() {
        mSelectedDepartment = NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT);
    }

}
