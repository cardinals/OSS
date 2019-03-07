package com.nobitastudio.oss.fragment;

import android.content.Context;
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

    class DiagnosisRecyclerViewAdapter extends BaseRecyclerViewAdapter<OSSOrder> {

        public DiagnosisRecyclerViewAdapter(Context ctx, List<OSSOrder> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_diagnosis;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, OSSOrder item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.diagnosis_linearLayout);
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

    private float mShadowAlpha = 1.0f;
    private int mShadowElevationDp = 10;
    private int mRadius = 15;

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

        DiagnosisRecyclerViewAdapter adapter = new DiagnosisRecyclerViewAdapter(getContext(), null);
        mWaitDiagnosisRecyclerView.setAdapter(adapter);
        mWaitDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mFinishedDiagnosisRecyclerView.setAdapter(adapter);
        mFinishedDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mUnfinishedDiagnosisRecyclerView.setAdapter(adapter);
        mUnfinishedDiagnosisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAllDiagnosisRecyclerView.setAdapter(adapter);
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

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("我的就诊");
        mTopBar.addRightImageButton(R.mipmap.ic_plus_round, R.id.topbar_right_plus_button).setOnClickListener(v ->
                showListPopView(v, Arrays.asList("挂号单项", "检查项", "手术项", "全部"),
                        (parent, view, position, id) -> {
                            popViewDismiss();
                            ToastUtils.showShort(position);
                        },
                        null));
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
