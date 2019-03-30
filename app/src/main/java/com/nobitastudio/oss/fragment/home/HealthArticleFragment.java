package com.nobitastudio.oss.fragment.home;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.activity.PlayVideoActivity;
import com.nobitastudio.oss.adapter.pager.UltraPagerAdapter;
import com.nobitastudio.oss.adapter.recyclerview.DoctorLectureRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.HeadlineRecycleViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class HealthArticleFragment extends StandardWithTobBarLayoutFragment {

    // 初始化为 健康头条  以及  名医讲堂
    public enum Pager {
        HEADLINE, DOCTOR_LECTURE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return HEADLINE;
                case 1:
                    return DOCTOR_LECTURE;
                default:
                    return HEADLINE;
            }
        }
    }

    // 轮播图
    private PagerAdapter mUltraPagerAdapter;

    // 健康头条,名师讲堂
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

    @BindView(R.id.ultraview_pager)
    UltraViewPager mUltraViewPager;
    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;

    Map<Pager, View> mPages;
    HeadlineRecycleViewAdapter mHeadlineRecycleViewAdapter;
    DoctorLectureRecyclerViewAdapter mDoctorLectureRecyclerViewAdapter;

    /**
     * 初始化健康头条，名医讲座  等等
     */
    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mHeadlineRecycleView = new RecyclerView(getContext());
        RecyclerView mDoctorLectureRecyclerView = new RecyclerView(getContext());
        mHeadlineRecycleViewAdapter = new HeadlineRecycleViewAdapter(getContext(), null);
        mDoctorLectureRecyclerViewAdapter = new DoctorLectureRecyclerViewAdapter(getContext(), null);
        mDoctorLectureRecyclerViewAdapter.setOnItemClickListener((v, pos) -> ActivityUtils.startActivity(new Intent(getContext(), PlayVideoActivity.class)));
        mHeadlineRecycleView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mHeadlineRecycleView.setAdapter(mHeadlineRecycleViewAdapter);
        mDoctorLectureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mDoctorLectureRecyclerView.setAdapter(mDoctorLectureRecyclerViewAdapter);

        mPages.put(Pager.HEADLINE, mHeadlineRecycleView);
        mPages.put(Pager.DOCTOR_LECTURE, mDoctorLectureRecyclerView);
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

    /**
     * 初始化tabs
     */
    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        QMUITabSegment.Tab healthArticle = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_crown),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_crown_selected),
                "健康头条", false
        );
        QMUITabSegment.Tab doctorLecture = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hat),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hat_selected),
                "名医讲堂", false
        );
        mTabSegment.addTab(healthArticle);
        mTabSegment.addTab(doctorLecture);
    }

    /**
     * 初始化医院活动mUltraPager
     */
    private void initUltraViewPager() {
        mUltraPagerAdapter = new UltraPagerAdapter(getContext(), null);
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mUltraViewPager.setAdapter(mUltraPagerAdapter);
        mUltraViewPager.setInfiniteLoop(true);
        mUltraViewPager.setAutoScroll(4000);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected Boolean topBarHavDivider() {
        return Boolean.FALSE;
    }

    @Override
    protected Boolean topBarIsTransparent() {
        return Boolean.TRUE;
    }

    @Override
    protected String getTopBarTitle() {
        return "健康资讯";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_health_article;
    }

    @Override
    protected void initLastCustom() {
        initUltraViewPager();
        initTabs();
        initPagers();
    }

}
