package com.nobitastudio.oss.fragment.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.OSSOrder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 订单
 */
public class OrderFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager :挂号订单  报告邮寄订单 开药订单 检查订单  手术订单  住院缴费订单
    enum Pager {
        REGISTER, EXPRESS, DRUG, CHECK, OPERATION, HOSPITALIZE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return REGISTER;
                case 1:
                    return EXPRESS;
                case 2:
                    return DRUG;
                case 3:
                    return CHECK;
                case 4:
                    return OPERATION;
                case 5:
                    return HOSPITALIZE;
                default:
                    return REGISTER;
            }
        }
    }

    class OrderRecyclerViewAdapter extends BaseRecyclerViewAdapter<OSSOrder> {

        public OrderRecyclerViewAdapter(Context ctx, List<OSSOrder> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_order;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, OSSOrder item) {
            initQMUILinearLayout(holder.getView(R.id.order_linearLayout));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;
    private HashMap<Pager, View> mPages;
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
            View page = mPages.get(Pager.getPagerFromPosition(position));
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

    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mRegisterOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mExpressOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mDrugOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mCheckOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mOperationOrderRecyclerView = new RecyclerView(getContext());
        RecyclerView mHospitalizeOrderRecyclerView = new RecyclerView(getContext());
        mPages.put(Pager.REGISTER, mRegisterOrderRecyclerView);
        mPages.put(Pager.EXPRESS, mExpressOrderRecyclerView);
        mPages.put(Pager.DRUG, mDrugOrderRecyclerView);
        mPages.put(Pager.CHECK, mCheckOrderRecyclerView);
        mPages.put(Pager.OPERATION, mOperationOrderRecyclerView);
        mPages.put(Pager.HOSPITALIZE, mHospitalizeOrderRecyclerView);

        OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(getContext(), null);
        mRegisterOrderRecyclerView.setAdapter(adapter);
        mRegisterOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mExpressOrderRecyclerView.setAdapter(adapter);
        mExpressOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mDrugOrderRecyclerView.setAdapter(adapter);
        mDrugOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mCheckOrderRecyclerView.setAdapter(adapter);
        mCheckOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mOperationOrderRecyclerView.setAdapter(adapter);
        mOperationOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mHospitalizeOrderRecyclerView.setAdapter(adapter);
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
    protected void initTopBarLast() {
        mTopBar.addRightImageButton(R.mipmap.ic_plus, R.id.topbar_right_plus_button).setOnClickListener(v ->
                showListPopView(v, Arrays.asList("待支付订单", "已支付订单", "已取消订单", "全部订单"),
                        (parent, view, position, id) -> {
                            popViewDismiss();
                            ToastUtils.showShort(position);
                        },
                        null));
    }

    @Override
    protected String getTopBarTitle() {
        return "我的订单";
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initLastCustom() {
        initTabs();
        initPagers();
    }
}
