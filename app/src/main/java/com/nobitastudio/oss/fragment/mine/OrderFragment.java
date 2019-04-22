package com.nobitastudio.oss.fragment.mine;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.OrderRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.nobitastudio.oss.model.enumeration.ItemType;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 订单
 */
public class OrderFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;
    private HashMap<ItemType, View> mPages;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            View page = mPages.get(ItemType.getFromPosition(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };

    List<OSSOrder> mOssOrders; // 所有订单情况  以下的订单均是该订单的子订单
    List<OSSOrder> mRegisterOrders; // 挂号订单
    List<OSSOrder> mExpressOrders;  // 报告邮寄订单
    List<OSSOrder> mDrugOrders; // 开药订单
    List<OSSOrder> mCheckOrders;  // 检查订单
    List<OSSOrder> mOperationOrders; // 手术订单
    List<OSSOrder> mHospitalizeOrders; // 住院缴费订单
    OrderRecyclerViewAdapter mRegisterOrdersAdapter;
    OrderRecyclerViewAdapter mExpressOrdersAdapter;
    OrderRecyclerViewAdapter mDrugOrdersAdapter;
    OrderRecyclerViewAdapter mCheckOrdersAdapter;
    OrderRecyclerViewAdapter mOperationOrdersAdapter;
    OrderRecyclerViewAdapter mHospitalizeOrdersAdapter;

    private void initBasic() {
        mOssOrders = new ArrayList<>();
        mRegisterOrders = new ArrayList<>(); // 挂号订单
        mExpressOrders = new ArrayList<>();  // 报告邮寄订单
        mDrugOrders = new ArrayList<>(); // 开药订单
        mCheckOrders = new ArrayList<>();  // 检查订单
        mOperationOrders = new ArrayList<>(); // 手术订单
        mHospitalizeOrders = new ArrayList<>(); // 住院缴费订单
        mRegisterOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mRegisterOrders);
        mExpressOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mExpressOrders);
        mDrugOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mDrugOrders);
        mCheckOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mCheckOrders);
        mOperationOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mOperationOrders);
        mHospitalizeOrdersAdapter = new OrderRecyclerViewAdapter(getContext(), mHospitalizeOrders);
    }

    // 刷新数据-进行网络连接
    @Override
    protected void refresh(boolean isCancelPull) {
        showNetworkLoadingTipDialog("正在加载");
        getAsyn(Arrays.asList("order", "queryAll", mNormalContainerHelper.getUser().getId().toString()), null,
                new ReflectStrategy<>(new TypeReference<List<OSSOrder>>() {
                }),
                new OkHttpUtil.SuccessHandler<List<OSSOrder>>() {
                    @Override
                    public void handle(List<OSSOrder> ossOrders) {
                        closeTipDialog();
                        mOssOrders.clear();
                        mOssOrders.addAll(ossOrders);
                        notifyDataChanged(null); // 刷新完成默认显示全部订单情况
                    }
                });
    }

    // 不进行网络连接 ,仅仅根性adapter的数据 用于更改界面
    private void notifyDataChanged(OrderState state) {
        mRegisterOrders.clear();
        mExpressOrders.clear();
        mDrugOrders.clear();
        mCheckOrders.clear();
        mOperationOrders.clear();
        mHospitalizeOrders.clear();
        mRegisterOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.REGISTER) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.REGISTER);
                    }
                }).collect(Collectors.toList()));
        mExpressOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.EXPRESS) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.EXPRESS);
                    }
                }).collect(Collectors.toList()));
        mDrugOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.DRUG) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.DRUG);
                    }
                }).collect(Collectors.toList()));
        mCheckOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.CHECK) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.CHECK);
                    }
                }).collect(Collectors.toList()));
        mOperationOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.OPERATION) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.OPERATION);
                    }
                }).collect(Collectors.toList()));
        mHospitalizeOrders.addAll(
                mOssOrders.stream().filter(item -> {
                    if (state != null) {
                        return item.getItemType().equals(ItemType.HOSPITALIZE) && item.getState().equals(state);
                    } else {
                        return item.getItemType().equals(ItemType.HOSPITALIZE);
                    }
                }).collect(Collectors.toList()));

        mRegisterOrdersAdapter.notifyDataSetChanged();
        mExpressOrdersAdapter.notifyDataSetChanged();
        mDrugOrdersAdapter.notifyDataSetChanged();
        mCheckOrdersAdapter.notifyDataSetChanged();
        mOperationOrdersAdapter.notifyDataSetChanged();
        mHospitalizeOrdersAdapter.notifyDataSetChanged();
    }

    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mRegisterOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mExpressOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mDrugOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mCheckOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mOperationOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mHospitalizeOrderRecyclerView = new RecyclerView(getContext());
        mPages.put(ItemType.REGISTER, mRegisterOrderRecyclerView);
        mPages.put(ItemType.EXPRESS, mExpressOrderRecyclerView);
        mPages.put(ItemType.DRUG, mDrugOrderRecyclerView);
        mPages.put(ItemType.CHECK, mCheckOrderRecyclerView);
        mPages.put(ItemType.OPERATION, mOperationOrderRecyclerView);
        mPages.put(ItemType.HOSPITALIZE, mHospitalizeOrderRecyclerView);

        mRegisterOrderRecyclerView.setAdapter(mRegisterOrdersAdapter);
        mRegisterOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mExpressOrderRecyclerView.setAdapter(mExpressOrdersAdapter);
        mExpressOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mDrugOrderRecyclerView.setAdapter(mDrugOrdersAdapter);
        mDrugOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mCheckOrderRecyclerView.setAdapter(mCheckOrdersAdapter);
        mCheckOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mOperationOrderRecyclerView.setAdapter(mOperationOrdersAdapter);
        mOperationOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mHospitalizeOrderRecyclerView.setAdapter(mHospitalizeOrdersAdapter);
        mHospitalizeOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                mViewPager.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        int space = QMUIDisplayHelper.dp2px(getContext(), 42);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);  // 超出自动适应，且可滚动
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setPadding(space, 0, space, 0);
        QMUITabSegment.Tab mRegisterOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor_selected),
                "挂号费", false
        );

        QMUITabSegment.Tab mExpressOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_express),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_express_selected),
                "邮寄费", false
        );

        QMUITabSegment.Tab mDrugOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug_selected),
                "药品费", false
        );

        QMUITabSegment.Tab mCheckOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check_selected),
                "检查订费", false
        );

        QMUITabSegment.Tab mOperationOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_operation),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_operation_selected),
                "手术费", false
        );

        QMUITabSegment.Tab mHospitalizeOrder = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher_selected),
                "住院缴费", false
        );

        mTabSegment.addTab(mRegisterOrder)
                .addTab(mExpressOrder)
                .addTab(mDrugOrder)
                .addTab(mCheckOrder)
                .addTab(mOperationOrder)
                .addTab(mHospitalizeOrder);
    }


    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
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
                mPullRefreshLayout.finishRefresh();
                refresh(false);
            }
        });
    }

    @Override
    protected void initTopBarLast() {
        mTopBar.addRightImageButton(R.mipmap.ic_plus, R.id.topbar_right_plus_button).setOnClickListener(v -> {
            List<String> chineseMean = OrderState.getChineseMeans();
            chineseMean.add("全部订单");
            showListPopView(v, chineseMean,
                    (parent, view, position, id) -> {
                        popViewDismiss();
                        notifyDataChanged(OrderState.getFromPosition(position));
                    },
                    null);
        });
    }

    @Override
    protected String getTopBarTitle() {
        return "我的订单";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initLastCustom() {
        initBasic();
        initTabs();
        initPagers();
    }
}
