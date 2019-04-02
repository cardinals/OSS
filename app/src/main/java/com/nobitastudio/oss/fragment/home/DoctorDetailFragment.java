package com.nobitastudio.oss.fragment.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.VisitRecycleViewAdapter;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarFragment;
import com.nobitastudio.oss.model.entity.CollectDoctor;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.User;
import com.nobitastudio.oss.model.entity.Visit;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    @BindView(R.id.imageview)
    ImageView mDoctorImageView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.introduction_zk_textview)
    TextView mIntroductionZKTextView;
    @BindView(R.id.introduction_zk_imageview)
    ImageView mIntroductionZKImageView;
    @BindView(R.id.speciality_zk_textview)
    TextView mSpecialityZKTextView;
    @BindView(R.id.speciality_zk_imageview)
    ImageView mSpecialityZKImageView;
    @BindView(R.id.introduction_content_textview)
    TextView mIntroductionContentTextView;
    @BindView(R.id.speciality_content_textview)
    TextView mSpecialityContentTextView;
    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;
    @BindView(R.id.introduction_solid_imageview)
    ImageView mIntroductionSolidImageView;
    @BindView(R.id.speciality_solid_imageview)
    ImageView mSpecialitySolidImageView;

    Doctor mSelectedDoctor;
    List<Doctor> mCollectDoctors;
    boolean mIntroductionIsZk = false; // 默认不展开介绍
    boolean mSpecialityIsZk = false; // 默认不展开特长
    List<Visit> mVisits; // 所有的visit
    List<Visit> mAvailableVisits; // 可挂号源
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

    @OnClick({R.id.fab,
            R.id.introduction_zk_textview, R.id.introduction_zk_imageview,
            R.id.speciality_zk_textview, R.id.speciality_zk_imageview})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (mCollectDoctors.contains(mSelectedDoctor)) {
                    postAsyn(Arrays.asList("collect-doctor", "cancel-collect"), null,
                            new CollectDoctor(mNormalContainerHelper.getUser().getId(), mSelectedDoctor.getId()));
                    mCollectDoctors.remove(mSelectedDoctor);
                    showSuccessTipDialog("您已取消收藏该医生");
                    mFab.setImageResource(R.drawable.ic_heart_white);
                } else {
                    postAsyn(Arrays.asList("collect-doctor"), null,
                            new CollectDoctor(mNormalContainerHelper.getUser().getId(), mSelectedDoctor.getId()));
                    mCollectDoctors.add(mSelectedDoctor);
                    showSuccessTipDialog("您已收藏该医生");
                    mFab.setImageResource(R.drawable.ic_heart_red);
                }
                break;
            case R.id.introduction_zk_textview:
            case R.id.introduction_zk_imageview:
                if (mIntroductionIsZk) {
                    // 是展开状态
                    mIntroductionZKTextView.setText("展开");
                    mIntroductionZKImageView.setImageResource(R.drawable.ic_arrow_down);
                    mIntroductionContentTextView.setSingleLine(true);
                } else {
                    // 不是展开状态
                    mIntroductionZKTextView.setText("收起");
                    mIntroductionZKImageView.setImageResource(R.drawable.ic_arrow_up);
                    mIntroductionContentTextView.setSingleLine(false);
                }
                mIntroductionIsZk = !mIntroductionIsZk;
                break;
            case R.id.speciality_zk_textview:
            case R.id.speciality_zk_imageview:
                if (mSpecialityIsZk) {
                    // 是展开状态
                    mSpecialityZKTextView.setText("展开");
                    mSpecialityZKImageView.setImageResource(R.drawable.ic_arrow_down);
                    mSpecialityContentTextView.setSingleLine(true);
                } else {
                    // 不是展开状态
                    mSpecialityZKTextView.setText("收起");
                    mSpecialityZKImageView.setImageResource(R.drawable.ic_arrow_up);
                    mSpecialityContentTextView.setSingleLine(false);
                }
                mSpecialityIsZk = !mSpecialityIsZk;
                break;
        }
    }

    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView availableVisitRecycleView = new RecyclerView(getContext());
        RecyclerView allVisitRecycleView = new RecyclerView(getContext());
        VisitRecycleViewAdapter availableVisitRecycleViewAdapter = new VisitRecycleViewAdapter(getContext(), mAvailableVisits);
        VisitRecycleViewAdapter allVisitRecycleViewAdapter = new VisitRecycleViewAdapter(getContext(), mVisits);

        availableVisitRecycleViewAdapter.setOnItemClickListener((view, pos) -> {
            mNormalContainerHelper.setSelectedVisit(mAvailableVisits.get(pos));
            startFragment(new PrepareRegisterFragment());
        });
        allVisitRecycleViewAdapter.setOnItemClickListener((view, pos) -> {
            if (mVisits.get(pos).getLeftAmount() == 0) {
                showInfoTipDialog("该号源以被预约完");
            } else {
                mNormalContainerHelper.setSelectedVisit(mVisits.get(pos));
                startFragment(new PrepareRegisterFragment());
            }
        });

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
        return mSelectedDoctor.getName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doctor_detail;
    }

    // 基础的初始化
    private void initBasic() {
        // 医生头像
        Glide.with(getContext()).load(ConstantContainer.OSS_SERVER_RUNTIME + mSelectedDoctor.getIconUrl()).into(mDoctorImageView);

        // 收藏情况
        mCollectDoctors = mNormalContainerHelper.getCollectDoctors();
        mFab.setImageResource(mCollectDoctors.contains(mSelectedDoctor) ? R.drawable.ic_heart_red : R.drawable.ic_heart_white);

        // 医生基础信息
        mIntroductionContentTextView.setText(mSelectedDoctor.getIntroduction());
        mSpecialityContentTextView.setText(mSelectedDoctor.getSpecialty());

        // 号源
        mVisits = new ArrayList<>();
        mAvailableVisits = new ArrayList<>();
        mVisits.addAll(mNormalContainerHelper.getVisits());
        mAvailableVisits.addAll(mVisits.stream().filter(item -> item.getLeftAmount() != 0).collect(Collectors.toList()));
    }

    @Override
    protected void initFirstCustom() {
        // 一定要在initLastCustom 之前进行。以为initTopbar需要使用作为标题
        mSelectedDoctor = mNormalContainerHelper.getSelectedDoctor();
    }

    @Override
    protected void initLastCustom() {
        initBasic();
        initSolidImage(mIntroductionSolidImageView, mSpecialitySolidImageView);
        initTabs();
        initPagers();
    }
}
