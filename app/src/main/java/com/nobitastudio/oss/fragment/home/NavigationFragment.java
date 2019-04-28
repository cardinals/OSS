package com.nobitastudio.oss.fragment.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.nobitastudio.oss.MainActivity;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DepartmentRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.ItemRecyclerViewAdapter;
import com.nobitastudio.oss.base.decorator.GridDividerItemDecoration;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.controller.amap.AmapTTSController;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.dto.ItemDescription;
import com.nobitastudio.oss.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

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

    int selectedNavTypeIndex = 0; //  默认驾车导航

    protected void initRecyclerView() {
        ItemRecyclerViewAdapter mItemAdapter = new ItemRecyclerViewAdapter(getContext(),
                Arrays.asList(new ItemDescription("驾车", R.mipmap.ic_car), new ItemDescription("骑行", R.mipmap.ic_bicycle),
                        new ItemDescription("步行", R.mipmap.ic_foot)));
        mItemAdapter.setOnItemClickListener((v, index) -> {
            selectedNavTypeIndex = index;
            requestLocationPermission();
        });
        mHospitalOutNavigationRecycleView.setAdapter(mItemAdapter);
        mHospitalOutNavigationRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHospitalOutNavigationRecycleView.addItemDecoration(new GridDividerItemDecoration(getContext(), 3));
        // 初始化科室列表
        initDepartmentRecyclerView();
    }

    // 调起 高德地图导航
    private void callAmap() {
        AmapNaviPage.getInstance().showRouteActivity(getContext(), getAmapNaviParamByIndex(selectedNavTypeIndex), mINaviInfoCallback);
    }

    // 申请定位权限
    private void requestLocationPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getBaseFragmentActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getBaseFragmentActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getBaseFragmentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionList.size() != 0) {
            requestPermissions(permissionList.toArray(new String[0]), ConstantContainer.REQUEST_CODE);
        } else {
            callAmap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ConstantContainer.REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showInfoTipDialog("您需同意使用定位等权限才可使用该功能", 2500l);
                            return;
                        }
                    }
                    callAmap();
                } else {
                    showInfoTipDialog("发生未知错误");
                }
                break;
        }
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
    protected void refresh(boolean isCancelPull) {
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
