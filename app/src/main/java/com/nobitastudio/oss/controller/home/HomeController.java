package com.nobitastudio.oss.controller.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.activity.PlayVideoActivity;
import com.nobitastudio.oss.adapter.pager.UltraPagerAdapter;
import com.nobitastudio.oss.adapter.recyclerview.DoctorLectureRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.HeadlineRecycleViewAdapter;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.DialogHelper;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.base.lab.fragment.QDWebViewFixFragment;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.home.DepartmentFragment;
import com.nobitastudio.oss.fragment.home.ExpressFragment;
import com.nobitastudio.oss.fragment.home.HealthArticleFragment;
import com.nobitastudio.oss.fragment.home.MedicalCardFragment;
import com.nobitastudio.oss.fragment.home.NavigationFragment;
import com.nobitastudio.oss.fragment.mine.ElectronicCaseFragment;
import com.nobitastudio.oss.fragment.mine.RegisterRecordFragment;
import com.nobitastudio.oss.model.common.ServiceResult;
import com.nobitastudio.oss.model.common.error.ErrorCode;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.model.enumeration.HealthArticleType;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class HomeController extends BaseController {

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

    DialogHelper mDialogHelper;

    @OnClick({R.id.register_linearLayout, R.id.pay_linearLayout, R.id.medical_card_linearLayout,
            R.id.navigation_linearLayout, R.id.express_linearLayout, R.id.register_record_linearLayout, R.id.electronic_case_linearLayout,
            R.id.health_article_linearLayout, R.id.smart_linearLayout, R.id.consulting_linearLayout, R.id.coming_soon_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_linearLayout:
                // 进入科室activity
                mHandler.startFragment(new DepartmentFragment());
                break;
            case R.id.pay_linearLayout:
                break;
            case R.id.medical_card_linearLayout:
                mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.NORMAL);
                mHandler.startFragment(new MedicalCardFragment());
                break;
            case R.id.navigation_linearLayout:
                mHandler.startFragment(new NavigationFragment());
                break;
            case R.id.express_linearLayout:
                mHandler.startFragment(new ExpressFragment());
                break;
            case R.id.register_record_linearLayout:
                mHandler.startFragment(new RegisterRecordFragment());
                break;
            case R.id.electronic_case_linearLayout:
                mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.ELECTRONIC_CASE);
                mHandler.startFragment(new MedicalCardFragment()); // 进入诊疗卡界面选择查看哪一张诊疗卡的病历信息
//                mDialogHelper.showAutoDialog("请输入诊疗卡密码(非登录密码)", mContext.getString(R.string.warm_prompt_electronic_case),
//                        "取消", (dialog, index) -> dialog.dismiss(),
//                        "确定", (dialog, index,content) -> {
//                            dialog.dismiss();
//                            mHandler.startFragment(new ElectronicCaseFragment());
//                        });
                break;
            case R.id.health_article_linearLayout:
                mHandler.startFragment(new HealthArticleFragment());
//                mHealthArticleFragment.refresh(false);
                break;
            case R.id.smart_linearLayout:
                break;
            case R.id.consulting_linearLayout:
                break;
            case R.id.coming_soon_linearLayout:
//                Toasty.error(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.success(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.info(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.warning(mContext, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                mContext.startActivity(new Intent(mContext, PlayVideoActivity.class));
//                ActivityUtils.startActivity(new Intent(mContext, PlayVideoActivity.class));
//                showNetworkLoadingTipDialog("测试", 1500);
                refresh(Boolean.FALSE);
                break;
            case R.id.topbar_right_setting_button:
                ToastUtils.showShort("进入天气预报");
                break;
        }
    }

    HashMap<HealthArticleFragment.Pager, View> mPages;
    List<HealthArticle> mHealthArticles;// 医院活动.健康头条.名医讲堂  全部
    List<HealthArticle> mHospitalActivities;// 医院活动
    List<HealthArticle> mHeadlines;// 健康头条
    List<HealthArticle> mDoctorLectures;// 名医讲堂

    TipDialogHelper mTipDialogHelper;
    PagerAdapter mUltraPagerAdapter; // 医院活动
    HeadlineRecycleViewAdapter mHeadlineRecycleViewAdapter; //头条
    DoctorLectureRecyclerViewAdapter mDoctorLectureRecyclerViewAdapter; // 名医讲座

    private PagerAdapter mPagerAdapter;  // 健康资讯
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    private void initQMUILinearLayout() {
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
        mQMUILinearLayoutHelper.init(mQMUILinearLayout);
    }

    private void initBasic(Context context) {
        mTipDialogHelper = new TipDialogHelper(context);
        mDialogHelper = new DialogHelper(context);

        // 初始化数据
        mHealthArticles = new ArrayList<>();
        mHospitalActivities = new ArrayList<>();
        mHeadlines = new ArrayList<>();
        mDoctorLectures = new ArrayList<>();

        // 初始化 adapter
        mUltraPagerAdapter = new UltraPagerAdapter(mContext, mHospitalActivities).setControllerClickHandler(mHandler);  // 轮播图
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
        mHeadlineRecycleViewAdapter = new HeadlineRecycleViewAdapter(getContext(), mHeadlines);// 健康头条
        mHeadlineRecycleViewAdapter.setOnItemClickListener((itemView, pos) -> {
            HealthArticle mSelectedHeadline = mHeadlines.get(pos);
            mNormalContainerHelper.setSelectedHealthArticle(mSelectedHeadline);
            Toasty.info(mContext,"id:" + mSelectedHeadline.getId() + ",url：" + mSelectedHeadline.getUrl()).show();
            mHandler.startFragment(new QDWebViewFixFragment());
        });
        mDoctorLectureRecyclerViewAdapter = new DoctorLectureRecyclerViewAdapter(getContext(), mDoctorLectures); // 名师讲堂
        mDoctorLectureRecyclerViewAdapter.setOnItemClickListener((v, pos) -> {
            HealthArticle mSelectedLecture = mDoctorLectures.get(pos);
            mNormalContainerHelper.setSelectedDoctorLecture(mSelectedLecture);
            Toasty.info(mContext,"id:" + mSelectedLecture.getId() + ",url：" + mSelectedLecture.getUrl()).show();
            mContext.startActivity(new Intent(mContext, PlayVideoActivity.class));
        });
    }

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
                refresh(Boolean.TRUE);
            }
        });
    }

    /**
     * 初始化tobBar
     */
    private void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
//        mTopBar.setTitle(getResources().getString(R.string.home));
        mTopBar.addLeftImageButton(R.mipmap.ic_qr_code, R.id.topbar_left_qr_code_button)
                .setOnClickListener(view -> ToastUtils.showShort("程序员小哥哥已累死,请给我们点时间吧~"));
//        TextView textView = new TextView(mContext);
//        textView.setText("这是搜索");
//        mTopBar.setCenterView(textView);  // 传入搜索的view
        mTopBar.addRightTextButton("多云转小雨 33℃", R.id.topbar_right_setting_button)
                .setOnClickListener(v -> ToastUtils.showShort("进入天气预报"));
        mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent, null));
    }

    // 刷新操作
    public HomeController refresh(Boolean isCancelPull) {
        // 获取 healthArticle
        getAsyn(Arrays.asList("health-article", "queryLatestArticles"), null,
                new ReflectStrategy<>(new TypeReference<List<HealthArticle>>() {
                }),
                new OkHttpUtil.SuccessHandler<List<HealthArticle>>() {
                    @Override
                    public void handle(List<HealthArticle> healthArticles) {
                        // 清除
                        mHealthArticles.clear();
                        mHospitalActivities.clear();
                        mHeadlines.clear();
                        mDoctorLectures.clear();

                        // 存储
                        mHealthArticles.addAll(healthArticles);
                        mHospitalActivities.addAll(healthArticles.stream().filter(item -> item.getType().equals(HealthArticleType.HOSPITAL_ACTIVITY)).collect(Collectors.toList()));
                        mHeadlines.addAll(healthArticles.stream().filter(item -> item.getType().equals(HealthArticleType.HEADLINE)).collect(Collectors.toList()));
                        mDoctorLectures.addAll(healthArticles.stream().filter(item -> item.getType().equals(HealthArticleType.DOCTOR_LECTURE)).collect(Collectors.toList()));

                        // notify  run on mainThread
                        runOnUiThread(() -> {
                            mHospitalActivityUltraViewPager.refresh();
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
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_home;
    }

    @Override
    public void initLast() {
        initBasic(mContext);
        initTopBar();
        initUltraViewPager();
        initQMUILinearLayout();
        initPullFreshLayout();
        initTabs();
        initPagers();
        // refresh 无用 .需要初始化完毕后进行调用
    }

    public HomeController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
        //mViewPager.
    }
}
