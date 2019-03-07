package com.nobitastudio.oss.controller.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.DepartmentFragment;
import com.nobitastudio.oss.fragment.HealthArticleFragment;
import com.nobitastudio.oss.fragment.LoginFragment;
import com.nobitastudio.oss.fragment.MedicalCardFragment;
import com.nobitastudio.oss.fragment.NavigationFragment;
import com.nobitastudio.oss.fragment.RegisterRecordFragment;
import com.nobitastudio.oss.fragment.TestFragment;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
    @BindView(R.id.controller_pager)
    ViewPager mViewPager;
    @BindView(R.id.controller_tabs)
    QMUITabSegment mTabSegment;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;
    @BindView(R.id.ultraview_pager)
    UltraViewPager mHospitalActivityUltraViewPager;

    @OnClick({R.id.register_linearLayout, R.id.pay_linearLayout, R.id.medical_card_linearLayout,
            R.id.navigation_linearLayout, R.id.consulting_linearLayout, R.id.register_record_linearLayout, R.id.case_history_linearLayout,
            R.id.health_article_linearLayout, R.id.smart_linearLayout, R.id.express_linearLayout, R.id.coming_soon_linearLayout})
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
            case R.id.consulting_linearLayout:
                break;
            case R.id.register_record_linearLayout:
                mHandler.startFragment(new RegisterRecordFragment());
                break;
            case R.id.case_history_linearLayout:
                break;
            case R.id.health_article_linearLayout:
                mHandler.startFragment(new HealthArticleFragment());
                break;
            case R.id.smart_linearLayout:
                break;
            case R.id.express_linearLayout:
                mHandler.startFragment(new LoginFragment());
                break;
            case R.id.coming_soon_linearLayout:
                Toasty.error(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                mHandler.startFragment(new TestFragment());
//                Toasty.success(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.info(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.warning(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.topbar_right_setting_button:
                ToastUtils.showShort("进入天气预报");
                break;
            default:
                showInfoTipDialog(NO_SUPPORT_VIEW_ID, 1500l);
                break;
        }
    }

    HashMap<HealthArticleFragment.Pager, View> mPages;
    HealthArticleFragment.HeadlineRecycleViewAdapter mHeadlineRecycleViewAdapter;
    HealthArticleFragment.DoctorLectureRecyclerViewAdapter mDoctorLectureRecyclerViewAdapter;
    ControllerClickHandler mHandler;
    List<HealthArticle> mHealthArticles;
    TipDialogHelper mTipDialogHelper;
    Context mContext;
    private PagerAdapter mUltraPagerAdapter = new PagerAdapter(){

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View root = LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_item, null);
//            if (mHealthArticles != null) {
            ImageView imageView = root.findViewById(R.id.imageview);
            if (position % 3 == 0) {
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t4).into(imageView);
            } else if (position % 3 == 1){
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t2).into(imageView);
            } else {
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t3).into(imageView);
            }
//            }
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
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

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };

    /**
     * 初始化方法
     */
    private void init() {
        initBasic();
        initTopBar();
        initUltraViewPager();
        initQMUILinearLayout();
        initPullFreshLayout();
        initTabs();
        initPagers();
    }

    private void initQMUILinearLayout() {
        float mShadowAlpha = 1.0f;
        int mShadowElevationDp = 10;
        int mRadius = 15;
        mQMUILinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(getContext(), mRadius),
                QMUIDisplayHelper.dp2px(getContext(), mShadowElevationDp), mShadowAlpha);
    }

    private void initBasic() {
        mTipDialogHelper = new TipDialogHelper(getContext());
    }

    /**
     * 初始化健康头条，名医讲座  等等
     */
    private void initPagers() {
        mPages = new HashMap<>();
        RecyclerView mHeadlineRecycleView = new RecyclerView(getContext());
        RecyclerView mDoctorLectureRecyclerView = new RecyclerView(getContext());
        mHeadlineRecycleViewAdapter = new HealthArticleFragment.HeadlineRecycleViewAdapter(getContext(), null);
        mDoctorLectureRecyclerViewAdapter = new HealthArticleFragment.DoctorLectureRecyclerViewAdapter(getContext(), null);
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

        mPages.put(HealthArticleFragment.Pager.HEADLINE, mHeadlineRecycleView);
        mPages.put(HealthArticleFragment.Pager.DOCTOR_LECTURE, mDoctorLectureRecyclerView);
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
        mHospitalActivityUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mHospitalActivityUltraViewPager.setAdapter(mUltraPagerAdapter);
//        mHospitalActivityUltraViewPager.setPageTransformer(false, new UltraScaleTransformer());  // 用于设置滑动动画 使用默认即可
        mHospitalActivityUltraViewPager.setInfiniteLoop(true);
        mHospitalActivityUltraViewPager.setAutoScroll(4000);
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
                mHealthArticles.remove(0);
                mHealthArticles.remove(1);
                mHealthArticles.remove(2);
                mHealthArticles.remove(3);
                mHealthArticles.remove(4);
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
        mTopBar.addLeftImageButton(R.mipmap.ic_qr_code, R.id.topbar_left_qr_code_button)
                .setOnClickListener(view -> ToastUtils.showShort("程序员小哥哥已累死,请给我们点时间吧~"));
        mTopBar.addRightTextButton("多云转小雨 33℃", R.id.topbar_right_setting_button)
                .setOnClickListener(v -> ToastUtils.showShort("进入天气预报"));
    }

    protected void showNetworkLoadingTipDialog(String detailMsg) {
        mTipDialogHelper.showNetworkLoadingTipDialog(detailMsg);
    }

    protected void closeTipDialog() {
        mTipDialogHelper.closeTipDialog();
    }

    protected void showErrorTipDialog(String errorMsg, Long delayMills) {
        mTipDialogHelper.showErrorTipDialog(errorMsg, delayMills, mTopBar);
    }

    protected void showInfoTipDialog(String infoMsg, Long delayMills) {
        mTipDialogHelper.showInfoTipDialog(infoMsg, delayMills, mTopBar);
    }

    protected void showSuccessTipDialog(String successMsg, Long delayMills) {
        mTipDialogHelper.showSuccessTipDialog(successMsg, delayMills, mTopBar);
    }

    public HomeController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.controller_home, this);
        ButterKnife.bind(this);
        init();
        //mViewPager.
    }
}
