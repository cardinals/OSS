package com.nobitastudio.oss.fragment.home;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.TypeReference;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DepartmentRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.ItemRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.SimpleRecycleViewAdapter;
import com.nobitastudio.oss.base.decorator.GridDividerItemDecoration;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.controller.amap.AmapTTSController;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.vo.ItemDescription;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

import static com.nobitastudio.oss.container.ConstantContainer.getAmapNaviParamByIndex;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class NavigationFragment extends StandardWithTobBarLayoutFragment {

    private INaviInfoCallback mINaviInfoCallback = new INaviInfoCallback() {
        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onGetNavigationText(String s) {
            amapTTSController.onGetNavigationText(s);
        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onArriveDestination(boolean b) {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onStopSpeaking() {
            amapTTSController.stopSpeaking();
        }

        @Override
        public void onReCalculateRoute(int i) {

        }

        @Override
        public void onExitPage(int i) {

        }

        @Override
        public void onStrategyChanged(int i) {

        }

        @Override
        public View getCustomNaviBottomView() {
            return null;
        }

        @Override
        public View getCustomNaviView() {
            return null;
        }

        @Override
        public void onArrivedWayPoint(int i) {

        }
    };

    @BindView(R.id.hospital_out_navigation_solid_imageview)
    ImageView mHospitalOutNavigationSolidImageView;
    @BindView(R.id.hospital_inner_navigation_solid_imageview)
    ImageView mHospitalInnerNavigationSolidImageView;
    @BindView(R.id.hospital_out_navigation_recyclerview)
    RecyclerView mHospitalOutNavigationRecycleView;
    @BindView(R.id.hospital_inner_navigation_recyclerview)
    RecyclerView mRecyclerView;

    AmapTTSController amapTTSController; // 语音合成
    List<Department> mDepartments;
    DepartmentRecyclerViewAdapter adapter;

    protected void initRecyclerView() {
        ItemRecyclerViewAdapter mItemAdapter = new ItemRecyclerViewAdapter(getContext(),
                Arrays.asList(new ItemDescription("驾车", R.mipmap.ic_car), new ItemDescription("骑行", R.mipmap.ic_bicycle),
                        new ItemDescription("步行", R.mipmap.ic_foot)));
        mItemAdapter.setOnItemClickListener((v, index) -> AmapNaviPage.getInstance().showRouteActivity(getContext(), getAmapNaviParamByIndex(index), mINaviInfoCallback));
        mHospitalOutNavigationRecycleView.setAdapter(mItemAdapter);
        mHospitalOutNavigationRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHospitalOutNavigationRecycleView.addItemDecoration(new GridDividerItemDecoration(getContext(), 3));
        // 初始化科室列表
        initDepartmentRecyclerView();
    }

    private void initDepartmentRecyclerView() {
        showNetworkLoadingTipDialog("正在加载科室列表信息");
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
            Department department = mDepartments.get(pos);
            showLongMessageDialog(department.getName(), generateDepartmentInfo(department),
                    null, null,
                    "知道了", (dialog, index) -> dialog.dismiss());
        });
        mRecyclerView.setAdapter(adapter);
    }

    //在科室列表生成科室基本信息
    private String generateDepartmentInfo(Department department) {
        return "楼层: " + department.getFloor() + "楼" + "\n\n" +
                "区域: " + department.getArea().name() + "区" + "\n\n" +
                "介绍: " + department.getIntroduction();
    }

    private void initTTS() {
        amapTTSController = AmapTTSController.getInstance(getContext());
        amapTTSController.init();
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
                        closeTipDialog();
                    }
                },
                new OkHttpUtil.FailHandler<List<Department>>() {
                    @Override
                    public void handle(ServiceResult<List<Department>> serviceResult) {
                        showInfoTipDialog(ErrorCode.get(serviceResult.getErrorCode()));
                        showErrorTipDialog("科室列表加载失败");
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        amapTTSController.destroy();
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected String getTopBarTitle() {
        return "导航导诊";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalOutNavigationSolidImageView, mHospitalInnerNavigationSolidImageView);
        initRecyclerView();
        initTTS();
    }
}
