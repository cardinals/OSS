package com.nobitastudio.oss.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.DepartmentFragment;
import com.nobitastudio.oss.fragment.MedicalCardFragment;
import com.nobitastudio.oss.fragment.NavigationFragment;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.CommonUtil;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class HomeController extends QMUIWindowInsetLayout {

    static final String NO_SUPPORT_VIEW_ID = "不支持的点击事件";

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;

    QMUITipDialog mQmuiTipDialog;
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
    HealthArticleRecycleViewAdapter recycleViewAdapter;

    ControllerClickHandler mHandler;

    List<HealthArticle> healthArticles;

    @OnClick({R.id.register_linearLayout, R.id.pay_linearLayout, R.id.medical_card_linearLayout, R.id.navigation_linearLayout,
            R.id.smart_linearLayout, R.id.consulting_linearLayout, R.id.coming_soon_linearLayout, R.id.test_linearLayout,
            R.id.register_detail_linearLayout, R.id.case_history_linearLayout, R.id.article_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_linearLayout:
                // 进入科室activity
                mHandler.startFragment(new DepartmentFragment());
                break;
            case R.id.pay_linearLayout:
                break;
            case R.id.medical_card_linearLayout:
                mHandler.startFragment(new MedicalCardFragment());
                break;
            case R.id.navigation_linearLayout:
                mHandler.startFragment(new NavigationFragment());
                break;
            case R.id.smart_linearLayout:
                break;
            case R.id.consulting_linearLayout:
                break;
            case R.id.coming_soon_linearLayout:
                break;
            case R.id.test_linearLayout:
                break;
            case R.id.register_detail_linearLayout:
                break;
            case R.id.case_history_linearLayout:
                break;
            case R.id.article_linearLayout:
                break;
            case R.id.topbar_right_setting_button:
                ToastUtils.showShort("进入天气预报");
                break;
            default:
                showInfoTipDialog(NO_SUPPORT_VIEW_ID, 1500l);
                break;
        }
    }

    /**
     * 初始化方法
     */
    private void init() {
        initTopBar();
        initPullFreshLayout();
        initUltraViewPager();
        initTabs();
        initData();
        initPagers();
    }

    private void initData() {
        healthArticles = new ArrayList<>();
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
        healthArticles.add(new HealthArticle());
    }

    /**
     * 初始化健康头条，名医讲座  等等
     */
    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mHealthArticleRecyclerView = new RecyclerView(getContext());
        RecyclerView doctorLectureRecyclerView = new RecyclerView(getContext());
        recycleViewAdapter = new HealthArticleRecycleViewAdapter(getContext(), healthArticles);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mHealthArticleRecyclerView.setLayoutManager(gridLayoutManager);
        mHealthArticleRecyclerView.setAdapter(recycleViewAdapter);

        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.test, null);

        mPages.put(Pager.HEALTH_ARTICLE, mHealthArticleRecyclerView);
        mPages.put(Pager.DOCTOR_LECTURE, view2);
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
                ContextCompat.getDrawable(getContext(), R.mipmap.tcm),
                ContextCompat.getDrawable(getContext(), R.mipmap.tcm_selected),
                "健康头条", true
        );
        QMUITabSegment.Tab doctorLecture = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.video),
                ContextCompat.getDrawable(getContext(), R.mipmap.video_selected),
                "名医讲堂", true
        );
        mTabSegment.addTab(healthArticle);
        mTabSegment.addTab(doctorLecture);
    }

    /**
     * 初始化医院活动
     */
    private void initUltraViewPager() {

    }

    /**
     * 初始化下拉事件
     */
    private void initPullFreshLayout() {
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
                healthArticles.remove(0);
                healthArticles.remove(1);
                healthArticles.remove(2);
                healthArticles.remove(3);
                healthArticles.remove(4);
                mPagerAdapter.notifyDataSetChanged();
                // 完成后调用finishRefresh关闭
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.finishRefresh();
                        ToastUtils.showShort("刷新成功");
                    }
                }, 2000);
            }
        });
    }

    /**
     * 初始化tobBar
     */
    private void initTopBar() {
        mTopBar.setTitle(getResources().getString(R.string.home));
        mTopBar.addLeftImageButton(R.mipmap.qr_code, R.id.topbar_left_qr_code_button)
                .setOnClickListener(view -> ToastUtils.showShort("程序员小哥哥已累死,请给我们点时间吧~"));
        mTopBar.addRightTextButton("多云转小雨 33℃", R.id.topbar_right_setting_button)
                .setOnClickListener(v -> ToastUtils.showShort("进入天气预报"));
    }

    protected void showNetworkLoadingTipDialog(String detailMsg) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(detailMsg == null ? "正在加载" : detailMsg)
                .create();
        mQmuiTipDialog.show();
        //ViewUtil.lockView(forgetPasswordTextView, enrollTextView, loginButton);
    }

    protected void closeTipDialog() {
        if (mQmuiTipDialog != null && mQmuiTipDialog.isShowing()) {
            mQmuiTipDialog.hide();
        }
    }

    protected void showErrorTipDialog(String errorMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(errorMsg == null ? "发送失败" : errorMsg)
                .create();
        mQmuiTipDialog.show();
        mTopBar.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    protected void showInfoTipDialog(String infoMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(infoMsg == null ? "请勿重复操作" : infoMsg)
                .create();
        mQmuiTipDialog.show();
        mTopBar.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    protected void showSuccessTipDialog(String successMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(successMsg == null ? "发送成功" : successMsg)
                .create();
        mQmuiTipDialog.show();
        mTopBar.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    /**
     * 初始化有哪些 pager
     */
    enum Pager {
        HEALTH_ARTICLE, DOCTOR_LECTURE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return HEALTH_ARTICLE;
                case 1:
                    return DOCTOR_LECTURE;
                default:
                    return HEALTH_ARTICLE;
            }
        }
    }

    public void setmHandler(ControllerClickHandler mHandler) {
        this.mHandler = mHandler;
    }

    static class HealthArticleRecycleViewAdapter extends BaseRecyclerAdapter<HealthArticle> {

        public HealthArticleRecycleViewAdapter(Context ctx, List<HealthArticle> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_health_article;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
            Glide.with(mContext).load(R.drawable.hospital_trademark).into(holder.getImageView(R.id.cover_imageView));
            holder.setText(R.id.title_textView, item.getTitle());
            holder.setText(R.id.type_textView, item.getType().name());
            holder.setText(R.id.publish_time_textView,CommonUtil.handleHealthNewsPublishTime(DateUtil.formatLocalDateTimeToSimpleString(item.getPublishTime())));
        }
    }

    public HomeController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.pager_home, this);
        ButterKnife.bind(this);
        init();
        //mViewPager.
    }
}
