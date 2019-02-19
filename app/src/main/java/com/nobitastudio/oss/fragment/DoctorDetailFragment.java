package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorDetailFragment extends StandardWithTobBarFragment {

    /**
     * 初始化有哪些 pager
     */
    enum Pager {
        AVAILABLE_VISIT, ALL_VISIT;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return AVAILABLE_VISIT;
                case 1:
                    return ALL_VISIT;
                default:
                    return AVAILABLE_VISIT;
            }
        }
    }

    public class VisitRecycleViewAdapter extends BaseRecyclerAdapter<Visit> {
        public VisitRecycleViewAdapter(Context ctx, List<Visit> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_visit;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Visit item) {
            TextView mDateTextView = holder.getTextView(R.id.date_textview);
            TextView mWeekTextView = holder.getTextView(R.id.week_textview);
            TextView mTimeSlotTextView = holder.getTextView(R.id.time_slot_textview);
            TextView mHospitalNameTextView = holder.getTextView(R.id.hospital_name_textview);
            TextView mSubMajorTextView = holder.getTextView(R.id.submajor_textview);
            TextView mAreaTextView = holder.getTextView(R.id.area_textview);
            QMUIRoundButton mGreenRoundButton = (QMUIRoundButton) holder.getView(R.id.green_roundbutton);
            QMUIRoundButton mRedRoundButton = (QMUIRoundButton) holder.getView(R.id.red_roundbutton);

            mDateTextView.setText(DateUtil.formatLocalDateTimeToSimpleString2(item.getDiagnosisTime()));
            mWeekTextView.setText(item.getDiagnosisTime().getDayOfWeek().name());
            mTimeSlotTextView.setText("上午");
            mHospitalNameTextView.setText("石河子大学医学院");
            mSubMajorTextView.setText("这是医生的亚专业");
            mAreaTextView.setText("A区");
            if (position > 3) {
                mGreenRoundButton.setVisibility(View.GONE);
                mRedRoundButton.setVisibility(View.VISIBLE);
            } else {
                mGreenRoundButton.setVisibility(View.VISIBLE);
                mRedRoundButton.setVisibility(View.GONE);
            }
        }
    }

    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;
    @BindView(R.id.introduction_solid_imageview)
    ImageView mIntroductionSolidImageView;
    @BindView(R.id.speciality_solid_imageview)
    ImageView mSpecialitySolidImageView;

    List<Visit> visits;
    public HashMap<Pager, View> mPages;
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
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            container.addView(page, params);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
//            if (mChildCount == 0) {
//                return POSITION_NONE;
//            }
//            return super.getItemPosition(object);
            return POSITION_NONE;   // 修复数据刷新但是高度不刷新的异常
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };

    @OnClick({R.id.fab})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                showSuccessTipDialog("您已成功收藏该医生~", null);
                mFab.setImageResource(R.drawable.ic_heart_red);
                break;
        }
    }

    private void initPagers() {
        mPages = new HashMap<>();
        visits = new ArrayList<>();
        List<Visit> visits2 = new ArrayList<>();
        visits.add(new Visit());
        visits.add(new Visit());
        visits.add(new Visit());
        visits.add(new Visit());
        visits.add(new Visit());
        visits.add(new Visit());
        visits.add(new Visit());

        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());
        visits2.add(new Visit());


        RecyclerView availableVisitRecycleView = new RecyclerView(getContext());
        RecyclerView allVisitRecycleView = new RecyclerView(getContext());
        VisitRecycleViewAdapter availableVisitRecycleViewAdapter = new VisitRecycleViewAdapter(getContext(), visits);

        availableVisitRecycleViewAdapter.setOnItemClickListener((view, pos) -> {
            startFragment(new PrepareRegisterFragment());
        });

        VisitRecycleViewAdapter allVisitRecycleViewAdapter = new VisitRecycleViewAdapter(getContext(), visits2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1);
        availableVisitRecycleView.setLayoutManager(gridLayoutManager);
        availableVisitRecycleView.setAdapter(availableVisitRecycleViewAdapter);
        allVisitRecycleView.setLayoutManager(gridLayoutManager2);
        allVisitRecycleView.setAdapter(allVisitRecycleViewAdapter);
        mPages.put(Pager.AVAILABLE_VISIT, availableVisitRecycleView);
        mPages.put(Pager.ALL_VISIT, allVisitRecycleView);
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
        QMUITabSegment.Tab availableVisit = new QMUITabSegment.Tab(null, null, "可用号源", true);
        QMUITabSegment.Tab allVisit = new QMUITabSegment.Tab(null, null, "全部号源", true);
        mTabSegment.addTab(availableVisit);
        mTabSegment.addTab(allVisit);
    }

    @Override
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return null;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mCollapsingTopBarLayout.setTitle("医生姓名2222");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doctor_detail;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(mIntroductionSolidImageView, mSpecialitySolidImageView);
        initTabs();
        initPagers();
    }

}
