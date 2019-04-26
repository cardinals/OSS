package com.nobitastudio.oss.fragment.mine;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DiagnosisRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.dto.RegistrationAll;
import com.nobitastudio.oss.model.enumeration.ItemType;
import com.nobitastudio.oss.util.CommonUtil;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class WaitDiagnosisFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager :待就诊  已就诊  未就诊   全部
    enum Pager {
        WAIT, FINISHED, UNFINISHED, ALL;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return WAIT;
                case 1:
                    return FINISHED;
                case 2:
                    return UNFINISHED;
                case 3:
                    return ALL;
                default:
                    return WAIT;
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

    List<RegistrationAll> mWaitRegistrations;
    List<RegistrationAll> mFinishRegistrations;
    List<RegistrationAll> mUnfinishRegistrations;
    List<RegistrationAll> mRegistrationAlls; // 挂号单封装对象的全部实体
    DiagnosisRecyclerViewAdapter mWaitRegistrationsAdapter;
    DiagnosisRecyclerViewAdapter mFinishRegistrationsAdapter;
    DiagnosisRecyclerViewAdapter mUnfinishRegistrationsAdapter;
    DiagnosisRecyclerViewAdapter mRegistrationAllsAdapter;

    private void initBasic() {
        mWaitRegistrations = new ArrayList<>();
        mFinishRegistrations = new ArrayList<>();
        mUnfinishRegistrations = new ArrayList<>();
        mRegistrationAlls = new ArrayList<>();

        mWaitRegistrationsAdapter = new DiagnosisRecyclerViewAdapter(getContext(), mWaitRegistrations);
        mFinishRegistrationsAdapter = new DiagnosisRecyclerViewAdapter(getContext(), mFinishRegistrations);
        mUnfinishRegistrationsAdapter = new DiagnosisRecyclerViewAdapter(getContext(), mUnfinishRegistrations);
        mRegistrationAllsAdapter = new DiagnosisRecyclerViewAdapter(getContext(), mRegistrationAlls);
    }

    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mWaitDiagnosisRecyclerView = new RecyclerView(getContext());
        RecyclerView mFinishedDiagnosisRecyclerView = new RecyclerView(getContext());
        RecyclerView mUnfinishedDiagnosisRecyclerView = new RecyclerView(getContext());
        RecyclerView mAllDiagnosisRecyclerView = new RecyclerView(getContext());
        mPages.put(Pager.WAIT, mWaitDiagnosisRecyclerView);
        mPages.put(Pager.FINISHED, mFinishedDiagnosisRecyclerView);
        mPages.put(Pager.UNFINISHED, mUnfinishedDiagnosisRecyclerView);
        mPages.put(Pager.ALL, mAllDiagnosisRecyclerView);

        mWaitDiagnosisRecyclerView.setAdapter(mWaitRegistrationsAdapter);
        mWaitDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mFinishedDiagnosisRecyclerView.setAdapter(mFinishRegistrationsAdapter);
        mFinishedDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mUnfinishedDiagnosisRecyclerView.setAdapter(mUnfinishRegistrationsAdapter);
        mUnfinishedDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAllDiagnosisRecyclerView.setAdapter(mRegistrationAllsAdapter);
        mAllDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
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
        mTabSegment.selectTab(mNormalContainerHelper.getDiagnosisTypePos()); // 设置选中位置
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
        QMUITabSegment.Tab mWaitDiagnosis = new QMUITabSegment.Tab(
                null,
                null,
                "待就诊", false
        );

        QMUITabSegment.Tab mFinishedDiagnosis = new QMUITabSegment.Tab(
                null,
                null,
                "已就诊", false
        );

        QMUITabSegment.Tab mUnfinishedDiagnosis = new QMUITabSegment.Tab(
                null,
                null,
                "未就诊", false
        );

        QMUITabSegment.Tab mAllDiagnosis = new QMUITabSegment.Tab(
                null,
                null,
                "全部", false
        );
        mTabSegment.addTab(mWaitDiagnosis)
                .addTab(mFinishedDiagnosis)
                .addTab(mUnfinishedDiagnosis)
                .addTab(mAllDiagnosis);
    }

    // 仅仅更改数据 (现在只支持挂号单项)
    private void notifyDataChanged(ItemType itemType) {
        // do nothing
        mWaitRegistrations.clear();
        mFinishRegistrations.clear();
        mUnfinishRegistrations.clear();

        mWaitRegistrations.addAll(mRegistrationAlls.stream().
                filter(item -> item.getVisit().getDiagnosisTime().isAfter(LocalDateTime.now(ZoneId.of("CTT")))).collect(Collectors.toList()));

        mFinishRegistrations.addAll(mRegistrationAlls.stream().filter(item -> {
            if (itemType != null) {
                return item.getOssOrder().getItemType().equals(itemType) && judgeIsFinishDiagnosis(item);
            } else {
                return judgeIsFinishDiagnosis(item);
            }
        }).collect(Collectors.toList()));

        mUnfinishRegistrations.addAll(mRegistrationAlls.stream().filter(item -> {
            if (itemType != null) {
                return item.getOssOrder().getItemType().equals(itemType) && judgeIsFinishDiagnosis(item);
            } else {
                return judgeIsFinishDiagnosis(item);
            }
        }).collect(Collectors.toList()));

        mWaitRegistrationsAdapter.notifyDataSetChanged();
        mFinishRegistrationsAdapter.notifyDataSetChanged();
        mUnfinishRegistrationsAdapter.notifyDataSetChanged();
        mRegistrationAllsAdapter.notifyDataSetChanged();
    }

    // todo 现在没有后台数据表支持判断是完成就诊还是 未就诊. 采用随机来模拟
    private boolean judgeIsFinishDiagnosis(RegistrationAll registrationAll) {
        return registrationAll.getVisit().getDiagnosisTime().isBefore(LocalDateTime.now(ZoneId.of("CTT"))) && CommonUtil.getRandom(0, 20) > 10;
    }

    @Override
    protected void refresh(boolean isCancelPull) {
        showNetworkLoadingTipDialog("正在加载");
        getAsyn(Arrays.asList("registration-record", "registrationAll", mNormalContainerHelper.getUser().getId().toString()), null,
                new ReflectStrategy<>(new TypeReference<List<RegistrationAll>>() {
                }),
                new OkHttpUtil.SuccessHandler<List<RegistrationAll>>() {
                    @Override
                    public void handle(List<RegistrationAll> registrationAlls) {
                        if (registrationAlls.size() > 0) {
                            closeTipDialog();
                            mRegistrationAlls.clear();
                            mRegistrationAlls.addAll(registrationAlls);
                            notifyDataChanged(null);
                        } else {
                            showInfoTipDialog("你尚未有任何就诊记录");
                        }
                    }
                });
    }


    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initTopBarLast() {
        mTopBar.addRightImageButton(R.mipmap.ic_plus, R.id.topbar_right_plus_button).setOnClickListener(v ->
                showListPopView(v, Arrays.asList("挂号单项", "检查项", "手术项", "全部"),
                        (parent, view, position, id) -> {
                            popViewDismiss();
                            if (position == 1 || position == 2) {
                                showInfoTipDialog("正在开发中");
                            } else if (position == 0) {
                                notifyDataChanged(ItemType.REGISTER);
                            } else {
                                notifyDataChanged(null);
                            }
                        },
                        null));
    }

    @Override
    protected String getTopBarTitle() {
        return "我的就诊";
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
