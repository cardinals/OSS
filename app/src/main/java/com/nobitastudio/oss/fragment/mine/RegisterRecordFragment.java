package com.nobitastudio.oss.fragment.mine;

import android.content.Context;
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
public class RegisterRecordFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager : 带就诊,已就诊,以取消,全部挂号单
    enum Pager {
        WAIT, FINISHED, CANCELED, ALL;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return WAIT;
                case 1:
                    return FINISHED;
                case 2:
                    return CANCELED;
                case 3:
                    return ALL;
                default:
                    return WAIT;
            }
        }
    }

    class RegisterRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<RegistrationRecord> {

        public RegisterRecordRecyclerViewAdapter(Context ctx, List<RegistrationRecord> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_register_history;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, RegistrationRecord item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.register_record_linearLayout);
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

        RecyclerView mWaitRecyclerView = new RecyclerView(getContext());
        RecyclerView mFinishedRecyclerView = new RecyclerView(getContext());
        RecyclerView mCanceledRecyclerView = new RecyclerView(getContext());
        RecyclerView mAllRecyclerView = new RecyclerView(getContext());

        RegisterRecordRecyclerViewAdapter adapter = new RegisterRecordRecyclerViewAdapter(getContext(),null);

        mWaitRecyclerView.setAdapter(adapter);
        mWaitRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mFinishedRecyclerView.setAdapter(adapter);
        mFinishedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mCanceledRecyclerView.setAdapter(adapter);
        mCanceledRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mAllRecyclerView.setAdapter(adapter);
        mAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mPages.put(Pager.WAIT,mWaitRecyclerView);
        mPages.put(Pager.FINISHED,mFinishedRecyclerView);
        mPages.put(Pager.CANCELED,mCanceledRecyclerView);
        mPages.put(Pager.ALL,mAllRecyclerView);

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
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected String getTopBarTitle() {
        return "挂号记录";
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initLastCustom() {
        initTabs();
        initPagers();
    }
}
