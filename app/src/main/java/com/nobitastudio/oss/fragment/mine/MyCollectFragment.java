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
import com.nobitastudio.oss.adapter.recyclerview.CollectionDoctorRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.HeadlineRecycleViewAdapter;
import com.nobitastudio.oss.base.inter.RecyclerViewClickHandler;
import com.nobitastudio.oss.controller.collection.OtherController;
import com.nobitastudio.oss.fragment.home.DoctorDetailFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.CollectDoctor;
import com.nobitastudio.oss.model.entity.Doctor;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.dto.DoctorAndDepartment;
import com.nobitastudio.oss.model.entity.Visit;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

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
 * @description
 */
public class MyCollectFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager :收藏的医生 ,收藏的文章
    enum Pager {
        DOCTOR, HEALTH_ARTICLE, OTHER;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return DOCTOR;
                case 1:
                    return HEALTH_ARTICLE;
                case 2:
                    return OTHER;
                default:
                    return DOCTOR;
            }
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

    CollectionDoctorRecyclerViewAdapter mCollectDoctorAdapter;
    HeadlineRecycleViewAdapter mHeadlineRecycleViewAdapter;
    List<HealthArticle> mHealthArticles;
    List<DoctorAndDepartment> mDoctorAndDepartments;
    RecyclerViewClickHandler<DoctorAndDepartment> handler;

    // 获取收藏的医生以及对应的科室信息
    private void getCollectDoctors() {
        showNetworkLoadingTipDialog("正在获取");
        getAsyn(Arrays.asList("collect-doctor", mNormalContainerHelper.getUser().getId().toString(), "dt-and-dp"), null,
                new ReflectStrategy<>(new TypeReference<List<DoctorAndDepartment>>() {
                }),
                new OkHttpUtil.SuccessHandler<List<DoctorAndDepartment>>() {
                    @Override
                    public void handle(List<DoctorAndDepartment> doctorAndDepartments) {
                        mNormalContainerHelper.setCollectDoctorAndDepartments(doctorAndDepartments);
                        mDoctorAndDepartments.clear();
                        mDoctorAndDepartments.addAll(mNormalContainerHelper.getCollectDoctorAndDepartments());
                        updateCollectDoctor();
                        if (mDoctorAndDepartments.size() == 0) {
                            showInfoTipDialog("你尚未收藏任何医生");
                        } else {
                            closeTipDialog();
                        }
                        mCollectDoctorAdapter.notifyDataSetChanged();
                    }
                });
    }

    // 更新容器中收藏的医生信息
    private void updateCollectDoctor() {
        mNormalContainerHelper.setCollectDoctors(mDoctorAndDepartments.stream().map(DoctorAndDepartment::getDoctor).collect(Collectors.toList()));
    }

    // 暂不实现
    private void getCollectHeadLine() {

    }

    private void initBasic() {
        mDoctorAndDepartments = new ArrayList<>();
        mHealthArticles = new ArrayList<>();
        handler = doctorAndDepartment ->
                mDialogHelper.showMessageNegativeDialog("提示", "确定要取消收藏" + doctorAndDepartment.getDoctor().getName() + "医生吗",
                        "取消收藏", (dialog, index) -> {
                            dialog.dismiss();
                            mDoctorAndDepartments.remove(doctorAndDepartment);
                            mCollectDoctorAdapter.notifyDataSetChanged();
                            updateCollectDoctor();
                            postAsyn(Arrays.asList("collect-doctor", "cancel-collect"), null,
                                    new CollectDoctor(mNormalContainerHelper.getUser().getId(), doctorAndDepartment.getDoctor().getId()));
                        }, "再想想", (dialog, index) -> dialog.dismiss());
        getCollectDoctors();
        getCollectHeadLine();
    }

    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mCollectDoctorRecyclerView = new RecyclerView(getContext());// 医生
        RecyclerView mCollectArticleRecyclerView = new RecyclerView(getContext()); // 健康资讯
        OtherController mOtherController = new OtherController(getContext(), null);
        mPages.put(Pager.DOCTOR, mCollectDoctorRecyclerView);
        mPages.put(Pager.HEALTH_ARTICLE, mCollectArticleRecyclerView);
        mPages.put(Pager.OTHER, mOtherController);
        mCollectDoctorAdapter = new CollectionDoctorRecyclerViewAdapter(getContext(), mDoctorAndDepartments, handler);
        mCollectDoctorAdapter.setOnItemClickListener((v, pos) -> {
            mNormalContainerHelper.setSelectedDepartment(mDoctorAndDepartments.get(pos).getDepartment())
                    .setSelectedDoctor(mDoctorAndDepartments.get(pos).getDoctor());
            // 获取指定医生下面的VISIT
            showNetworkLoadingTipDialog("正在查询号源信息");
            getAsyn(Arrays.asList("visit", "get-by-doctor", mDoctorAndDepartments.get(pos).getDoctor().getId().toString()), null,
                    new ReflectStrategy<>(new TypeReference<List<Visit>>() {
                    }),
                    new OkHttpUtil.SuccessHandler<List<Visit>>() {
                        @Override
                        public void handle(List<Visit> visits) {
                            closeTipDialog();
                            mNormalContainerHelper.setVisits(visits);
                            startFragment(new DoctorDetailFragment());
                        }
                    });
        });
        mHeadlineRecycleViewAdapter = new HeadlineRecycleViewAdapter(getContext(), mHealthArticles);
        mCollectDoctorRecyclerView.setAdapter(mCollectDoctorAdapter);
        mCollectDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mCollectArticleRecyclerView.setAdapter(mHeadlineRecycleViewAdapter);
        mCollectArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
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
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        QMUITabSegment.Tab mCollectDoctor = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor_selected),
                "医生", false
        );

        QMUITabSegment.Tab mHealthArticle = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_article),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_article_selected),
                "健康资讯", false
        );

        QMUITabSegment.Tab mCategory = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_category),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_category_selected),
                "其他类别", false
        );

        mTabSegment.addTab(mCollectDoctor)
                .addTab(mHealthArticle)
                .addTab(mCategory);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected String getTopBarTitle() {
        return "我的收藏";
    }

    @Override
    public void onResume() {
        super.onResume();
        getCollectDoctors();
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
