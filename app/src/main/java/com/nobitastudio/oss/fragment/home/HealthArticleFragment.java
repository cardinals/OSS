package com.nobitastudio.oss.fragment.home;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.ActivityUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.activity.PlayVideoActivity;
import com.nobitastudio.oss.adapter.pager.UltraPagerAdapter;
import com.nobitastudio.oss.adapter.recyclerview.DoctorLectureRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.HeadlineRecycleViewAdapter;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.base.lab.fragment.QDWebViewFixFragment;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.enumeration.HealthArticleType;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

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

    // 健康头条,名师讲堂
    private PagerAdapter mPagerAdapter;

    @BindView(R.id.ultraview_pager)
    UltraViewPager mUltraViewPager;
    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;

    Map<Pager, View> mPages;
    // 轮播图
    PagerAdapter mUltraPagerAdapter;
    HeadlineRecycleViewAdapter mHeadlineRecycleViewAdapter;
    DoctorLectureRecyclerViewAdapter mDoctorLectureRecyclerViewAdapter;

    List<HealthArticle> mTopHeadlines;// 医院活动  默认选取健康头条的前五个
    List<HealthArticle> mHeadlines;// 健康头条
    List<HealthArticle> mDoctorLectures;// 名医讲堂

    /**
     * 初始化健康头条，名医讲座  等等
     */
    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mHeadlineRecycleView = new RecyclerView(getContext());
        RecyclerView mDoctorLectureRecyclerView = new RecyclerView(getContext());
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
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mUltraViewPager.setAdapter(mUltraPagerAdapter);
        mUltraViewPager.setInfiniteLoop(true);
        mUltraViewPager.setAutoScroll(4000);
    }

    private void initBasic() {
        ControllerClickHandler mHandler = new ControllerClickHandler() {
            @Override
            public void startFragment(BaseFragment target) {
                HealthArticleFragment.this.startFragment(target);
            }

            @Override
            public void startFragmentAndDestroyCurrent(BaseFragment targetFragment) {
                HealthArticleFragment.this.startFragmentAndDestroyCurrent(targetFragment);
            }
        };

        mTopHeadlines = new ArrayList<>();
        mHeadlines = new ArrayList<>();
        mDoctorLectures = new ArrayList<>();

        mUltraPagerAdapter = new UltraPagerAdapter(getContext(), mTopHeadlines).setControllerClickHandler(mHandler);
        mPagerAdapter = new PagerAdapter() {

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
                View page = mPages.get(HealthArticleFragment.Pager.getPagerFromPosition(position));
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
        }; // pager
        // 初始化 adapter
        mHeadlineRecycleViewAdapter = new HeadlineRecycleViewAdapter(getContext(), mHeadlines);// 健康头条
        mHeadlineRecycleViewAdapter.setOnItemClickListener((itemView, pos) -> {
            HealthArticle mSelectedHeadline = mHeadlines.get(pos);
            mNormalContainerHelper.setSelectedHealthArticle(mSelectedHeadline);
            Toasty.info(getContext(), "id:" + mSelectedHeadline.getId() + ",url：" + mSelectedHeadline.getUrl()).show();
            startFragment(new QDWebViewFixFragment());
        });
        mDoctorLectureRecyclerViewAdapter = new DoctorLectureRecyclerViewAdapter(getContext(), mDoctorLectures); // 名师讲堂
        mDoctorLectureRecyclerViewAdapter.setOnItemClickListener((v, pos) -> {
            HealthArticle mSelectedLecture = mDoctorLectures.get(pos);
            mNormalContainerHelper.setSelectedDoctorLecture(mSelectedLecture);
            Toasty.info(getContext(), "id:" + mSelectedLecture.getId() + ",url：" + mSelectedLecture.getUrl()).show();
            startActivity(new Intent(getContext(), PlayVideoActivity.class));
        });
    }

    // 刷新操作
    @Override
    protected void refresh(boolean isCancelPull) {
        // 获取 healthArticle
        getAsyn(Arrays.asList("health-article", "queryMore"), ConstantContainer.GET_PAGER_PARAMS,
                new ReflectStrategy<>(new TypeReference<List<HealthArticle>>() {
                }),
                new OkHttpUtil.SuccessHandler<List<HealthArticle>>() {
                    @Override
                    public void handle(List<HealthArticle> healthArticles) {
                        // 清除
                        mTopHeadlines.clear();
                        mHeadlines.clear();
                        mDoctorLectures.clear();

                        // 存储
                        List<HealthArticle> mAuxHealthArticles = healthArticles.stream().filter(item -> item.getType().equals(HealthArticleType.HEADLINE))
                                .collect(Collectors.toList());
                        if (mAuxHealthArticles.size() < 5) {
                            // 数量不足
                            mTopHeadlines.addAll(mAuxHealthArticles);
                            // mHeadlines 不需要减少
                            mHeadlines.addAll(mAuxHealthArticles);
                        } else {
                            // 正常数量
                            mTopHeadlines.addAll(mAuxHealthArticles.subList(0, 5));
                            // 移除前五条
                            mHeadlines.addAll(mAuxHealthArticles.subList(5, mAuxHealthArticles.size()));
                        }
                        mDoctorLectures.addAll(healthArticles.stream().filter(item -> item.getType().equals(HealthArticleType.DOCTOR_LECTURE)).collect(Collectors.toList()));

                        // notify  run on mainThread
                        runOnUiThread(() -> {
                            mUltraViewPager.refresh();
                            mHeadlineRecycleViewAdapter.notifyDataSetChanged();
                            mDoctorLectureRecyclerViewAdapter.notifyDataSetChanged();
                        });
                        if (isCancelPull) {
                            mPullRefreshLayout.finishRefresh();
                        }
                    }
                }, new OkHttpUtil.FailHandler<List<HealthArticle>>() {
                    @Override
                    public void handle(ServiceResult<List<HealthArticle>> serviceResult) {
                        showInfoTipDialog(ErrorCode.get(serviceResult.getErrorCode()));
                        if (isCancelPull) {
                            mPullRefreshLayout.finishRefresh();
                        }
                    }
                });
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
                // 刷新操作
                refresh(Boolean.TRUE);
            }
        });
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
        initBasic();
        initUltraViewPager();
        initTabs();
        initPagers();
    }

}
