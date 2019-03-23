package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.VisitRecycleViewAdapter;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarFragment;
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
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
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

    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.pagers)
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
        availableVisitRecycleView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        availableVisitRecycleView.setAdapter(availableVisitRecycleViewAdapter);
        allVisitRecycleView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
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
    protected Boolean isSpecialTopBar() {
        return Boolean.TRUE;
    }

    @Override
    protected void handleSpecialTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
        mCollapsingTopBarLayout.setTitle(getTopBarTitle());
    }

    @Override
    protected String getTopBarTitle() {
        return "医生姓名2222";
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
