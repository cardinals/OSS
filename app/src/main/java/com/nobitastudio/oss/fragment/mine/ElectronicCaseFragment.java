package com.nobitastudio.oss.fragment.mine;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.RegistrationRecord;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 订单详情
 */
public class ElectronicCaseFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager : 门诊记录  住院记录  急诊记录
    enum Pager {
        OUTPATIENT, HOSPITALIZE, EMERGENCY;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return OUTPATIENT;
                case 1:
                    return HOSPITALIZE;
                case 2:
                    return EMERGENCY;
                default:
                    return OUTPATIENT;
            }
        }
    }

    // 门诊记录
    public static class OutpatientRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

        public OutpatientRecordRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_outpatient;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.outpatient_linearLayout);
            mLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    // 住院记录
    public static class HospitalizeRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

        public HospitalizeRecordRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_hospitalize;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.hospitalize_linearLayout);
            mLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    // 急诊记录
    public static class EmergencyRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

        public EmergencyRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_emergency;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.emergency_linearLayout);
            mLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
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

    private static float mShadowAlpha = 1.0f;
    private static int mShadowElevationDp = 10;
    private static int mRadius = 15;

    private void initPagers() {
        mPages = new HashMap<>();

        RecyclerView mOutpatientRecyclerView = new RecyclerView(getContext());
        RecyclerView mHospitalizeRecyclerView = new RecyclerView(getContext());
        RecyclerView mEmergencyRecyclerView = new RecyclerView(getContext());

        OutpatientRecordRecyclerViewAdapter mOutpatientRecordRecyclerViewAdapter = new OutpatientRecordRecyclerViewAdapter(getContext(), null);
        HospitalizeRecordRecyclerViewAdapter mHospitalizeRecordRecyclerViewAdapter = new HospitalizeRecordRecyclerViewAdapter(getContext(), null);
        EmergencyRecyclerViewAdapter mEmergencyRecyclerViewAdapter = new EmergencyRecyclerViewAdapter(getContext(), null);

        mOutpatientRecordRecyclerViewAdapter.setOnItemClickListener((view,pos) -> startFragment(new ElectronicCaseDetailFragment()));// 进入电子病历详情

        mOutpatientRecyclerView.setAdapter(mOutpatientRecordRecyclerViewAdapter);
        mOutpatientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mHospitalizeRecyclerView.setAdapter(mHospitalizeRecordRecyclerViewAdapter);
        mHospitalizeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mEmergencyRecyclerView.setAdapter(mEmergencyRecyclerViewAdapter);
        mEmergencyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mPages.put(Pager.OUTPATIENT, mOutpatientRecyclerView);
        mPages.put(Pager.HOSPITALIZE, mHospitalizeRecyclerView);
        mPages.put(Pager.EMERGENCY, mEmergencyRecyclerView);

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
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        QMUITabSegment.Tab mOutpatient = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hospital),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hospital_selected),
                "门诊记录", false
        );

        QMUITabSegment.Tab mHospitalize = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher_selected),
                "住院记录", false
        );

        QMUITabSegment.Tab mEmergency = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_ambulance),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_ambulance_selected),
                "急诊记录", false
        );

        mTabSegment.addTab(mOutpatient)
                .addTab(mHospitalize)
                .addTab(mEmergency);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("电子病历");
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electronic_case;
    }

    @Override
    protected void initLastCustom() {
        initTabs();
        initPagers();
    }
}
