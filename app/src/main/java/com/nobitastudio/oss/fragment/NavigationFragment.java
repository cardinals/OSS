package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.adapter.SimpleRecycleViewAdapter;
import com.nobitastudio.oss.base.decorator.GridDividerItemDecoration;
import com.nobitastudio.oss.model.entity.Department;
import com.nobitastudio.oss.model.vo.ItemDescription;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationFragment extends StandardWithTobBarLayoutFragment {

    static class ItemRecyclerViewAdapter extends BaseRecyclerAdapter<ItemDescription> {

        public ItemRecyclerViewAdapter(Context ctx, List<ItemDescription> data) {
            super(ctx, data);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_info;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, ItemDescription item) {
            holder.getTextView(R.id.item_name).setText(item.getName());
            if (item.getIconRes() != 0) {
                holder.getImageView(R.id.item_icon).setImageResource(item.getIconRes());
            }
        }
    }

    @BindView(R.id.hospital_out_navigation_solid_imageview)
    ImageView mHospitalOutNavigationSolidImageView;
    @BindView(R.id.hospital_inner_navigation_solid_imageview)
    ImageView mHospitalInnerNavigationSolidImageView;
    @BindView(R.id.hospital_out_navigation_recyclerview)
    RecyclerView mHospitalOutNavigationRecycleView;
    @BindView(R.id.hospital_inner_navigation_recyclerview)
    RecyclerView mHospitalInnerNavigationRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;

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
                mEmptyView.postDelayed(() -> mPullRefreshLayout.finishRefresh(), 1500l);
            }
        });
    }

    protected void initData() {
        mEmptyView.hide();
        ItemRecyclerViewAdapter mItemAdapter = new ItemRecyclerViewAdapter(getContext(),
                Arrays.asList(new ItemDescription("驾车", R.mipmap.ic_car_big), new ItemDescription("步行", R.mipmap.foot)));
        mItemAdapter.setOnItemClickListener((v, pos) -> ToastUtils.showShort(pos));
        mHospitalOutNavigationRecycleView.setAdapter(mItemAdapter);
        mHospitalOutNavigationRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mHospitalOutNavigationRecycleView.addItemDecoration(new GridDividerItemDecoration(getContext(), 2));
        initListView();
    }

    private void initListView() {
        List<Department> departments = new ArrayList<>();
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
        mHospitalInnerNavigationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
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
            Department department = departments.get(pos);
            showLongMessageDialog(department.getName(), generateDepartmentInfo(department),
                    "关闭", (dialog, index) -> dialog.dismiss(),
                    null, null);
        });
        mHospitalInnerNavigationRecyclerView.setAdapter(adapter);
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
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mTopBar.setTitle("导航导诊");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mHospitalOutNavigationSolidImageView, mHospitalInnerNavigationSolidImageView);
        initData();
    }
}
