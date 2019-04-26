package com.nobitastudio.oss.fragment.mine;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.RegisterRecordRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.home.RegisterSuccessFragment;
import com.nobitastudio.oss.fragment.home.WaitingPayRegisterFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.dto.RegistrationAll;
import com.nobitastudio.oss.model.enumeration.OrderState;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
 * @description 订单详情
 */
public class RegisterRecordFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager : 带就诊,已就诊,以取消,全部挂号单
    enum Pager {
        WAIT_PAY, HAVE_PAY, CANCELED, ALL;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return WAIT_PAY;
                case 1:
                    return HAVE_PAY;
                case 2:
                    return CANCELED;
                case 3:
                    return ALL;
                default:
                    return WAIT_PAY;
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

    List<RegistrationAll> mWaitPayRegistrations;
    List<RegistrationAll> mHavePayRegistrations;
    List<RegistrationAll> mCancelPayRegistrations;
    List<RegistrationAll> mRegistrationAlls; // 挂号单封装对象的全部实体
    RegisterRecordRecyclerViewAdapter mWaitPayRegistrationsAdapter;
    RegisterRecordRecyclerViewAdapter mHavePayRegistrationsAdapter;
    RegisterRecordRecyclerViewAdapter mCancelPayRegistrationsAdapter;
    RegisterRecordRecyclerViewAdapter mRegistrationAllsAdapter;

    private void initBasic() {
        mWaitPayRegistrations = new ArrayList<>();
        mHavePayRegistrations = new ArrayList<>();
        mCancelPayRegistrations = new ArrayList<>();
        mRegistrationAlls = new ArrayList<>();

        mWaitPayRegistrationsAdapter = new RegisterRecordRecyclerViewAdapter(getContext(), mWaitPayRegistrations);
        mHavePayRegistrationsAdapter = new RegisterRecordRecyclerViewAdapter(getContext(), mHavePayRegistrations);
        mCancelPayRegistrationsAdapter = new RegisterRecordRecyclerViewAdapter(getContext(), mCancelPayRegistrations);
        mRegistrationAllsAdapter = new RegisterRecordRecyclerViewAdapter(getContext(), mRegistrationAlls);

        mWaitPayRegistrationsAdapter.setOnItemClickListener((itemView, pos) -> onClick(mWaitPayRegistrations.get(pos)));
        mHavePayRegistrationsAdapter.setOnItemClickListener((itemView, pos) -> onClick(mHavePayRegistrations.get(pos)));
        mCancelPayRegistrationsAdapter.setOnItemClickListener((itemView, pos) -> onClick(mCancelPayRegistrations.get(pos)));
        mRegistrationAllsAdapter.setOnItemClickListener((itemView, pos) -> onClick(mRegistrationAlls.get(pos)));
    }

    // 每种类型点击时的反应
    private void onClick(RegistrationAll registrationAll) {
        if (registrationAll.getOssOrder().getState().equals(OrderState.WAITING_PAY)) {
            mNormalContainerHelper.setSelectedDepartment(registrationAll.getDepartment());
            mNormalContainerHelper.setSelectedDoctor(registrationAll.getDoctor());
            mNormalContainerHelper.setSelectedVisit(registrationAll.getVisit());
            mNormalContainerHelper.setSelectedMedicalCard(registrationAll.getMedicalCard());
            mNormalContainerHelper.setRegistrationRecord(registrationAll.getRegistrationRecord());
            Duration duration = Duration.between(registrationAll.getOssOrder().getCreateTime(), LocalDateTime.now(ZoneId.of("CTT")));
            long leftTime = 1800 - duration.toMillis() / 1000;
            mNormalContainerHelper.setLeftTime(leftTime < 0 ? 0 : Integer.valueOf(String.valueOf(leftTime)));
            RegisterRecordFragment.this.startFragment(new WaitingPayRegisterFragment());
        } else if (registrationAll.getOssOrder().getState().equals(OrderState.HAVE_PAY)) {
            mNormalContainerHelper.setSelectedDepartment(registrationAll.getDepartment());
            mNormalContainerHelper.setSelectedDoctor(registrationAll.getDoctor());
            mNormalContainerHelper.setSelectedVisit(registrationAll.getVisit());
            mNormalContainerHelper.setSelectedMedicalCard(registrationAll.getMedicalCard());
            mNormalContainerHelper.setRegistrationRecord(registrationAll.getRegistrationRecord());
            startFragment(new RegisterSuccessFragment());
        } else if (registrationAll.getOssOrder().getState().equals(OrderState.CANCEL_PAY) || registrationAll.getOssOrder().getState().equals(OrderState.AUTO_CANCEL_PAY)) {
            showInfoTipDialog("您已取消该挂号");
        }
    }

    // 仅仅改变数据显示
    private void notifyDataChanged() {
        mWaitPayRegistrations.clear();
        mHavePayRegistrations.clear();
        mCancelPayRegistrations.clear();

        mWaitPayRegistrations.addAll(mRegistrationAlls.stream().
                filter(item -> item.getOssOrder().getState().equals(OrderState.WAITING_PAY)).collect(Collectors.toList()));

        mHavePayRegistrations.addAll(mRegistrationAlls.stream().
                filter(item -> item.getOssOrder().getState().equals(OrderState.HAVE_PAY)).collect(Collectors.toList()));

        mCancelPayRegistrations.addAll(mRegistrationAlls.stream().
                filter(item -> item.getOssOrder().getState().equals(OrderState.CANCEL_PAY) || item.getOssOrder().getState().equals(OrderState.AUTO_CANCEL_PAY))
                .collect(Collectors.toList()));

        mWaitPayRegistrationsAdapter.notifyDataSetChanged();
        mHavePayRegistrationsAdapter.notifyDataSetChanged();
        mCancelPayRegistrationsAdapter.notifyDataSetChanged();
        mRegistrationAllsAdapter.notifyDataSetChanged();
    }

    private void initPagers() {
        mPages = new HashMap<>();

        RecyclerView mWaitRecyclerView = new RecyclerView(getContext());
        RecyclerView mFinishedRecyclerView = new RecyclerView(getContext());
        RecyclerView mCanceledRecyclerView = new RecyclerView(getContext());
        RecyclerView mAllRecyclerView = new RecyclerView(getContext());

        mWaitRecyclerView.setAdapter(mWaitPayRegistrationsAdapter);
        mWaitRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mFinishedRecyclerView.setAdapter(mHavePayRegistrationsAdapter);
        mFinishedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mCanceledRecyclerView.setAdapter(mCancelPayRegistrationsAdapter);
        mCanceledRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAllRecyclerView.setAdapter(mRegistrationAllsAdapter);
        mAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mPages.put(Pager.WAIT_PAY, mWaitRecyclerView);
        mPages.put(Pager.HAVE_PAY, mFinishedRecyclerView);
        mPages.put(Pager.CANCELED, mCanceledRecyclerView);
        mPages.put(Pager.ALL, mAllRecyclerView);

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
        QMUITabSegment.Tab mWaitRegisterRecord = new QMUITabSegment.Tab(
                null,
                null,
                "待支付", false
        );

        QMUITabSegment.Tab mFinishedRegisterRecord = new QMUITabSegment.Tab(
                null,
                null,
                "已支付", false
        );

        QMUITabSegment.Tab mCanceledRegisterRecord = new QMUITabSegment.Tab(
                null,
                null,
                "已取消", false
        );

        QMUITabSegment.Tab mAllRegisterRecord = new QMUITabSegment.Tab(
                null,
                null,
                "全部挂号单", false
        );

        mTabSegment.addTab(mWaitRegisterRecord)
                .addTab(mFinishedRegisterRecord)
                .addTab(mCanceledRegisterRecord)
                .addTab(mAllRegisterRecord);
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
                            notifyDataChanged();
                        } else {
                            showInfoTipDialog("你尚未有任何挂号记录");
                        }
                    }
                });
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
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected String getTopBarTitle() {
        return "挂号记录";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initLastCustom() {
        initBasic();
        initTabs();
        initPagers();
    }
}
