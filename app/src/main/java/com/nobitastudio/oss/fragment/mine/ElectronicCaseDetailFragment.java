package com.nobitastudio.oss.fragment.mine;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.controller.electroniccase.CheckController;
import com.nobitastudio.oss.controller.electroniccase.DiagnosisController;
import com.nobitastudio.oss.controller.electroniccase.DrugController;
import com.nobitastudio.oss.controller.electroniccase.OtherAdviseController;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 订单
 */
public class ElectronicCaseDetailFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager :诊断医嘱  药品医嘱  检查医嘱  其他医嘱
    enum Pager {
        DIAGNOSIS, DRUG, CHECK, OTHER_ADVISE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return DIAGNOSIS;
                case 1:
                    return DRUG;
                case 2:
                    return CHECK;
                case 3:
                    return OTHER_ADVISE;
                default:
                    return DIAGNOSIS;
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

    private void initPagers() {
        mPages = new HashMap<>();

        mPages.put(Pager.DIAGNOSIS, new DiagnosisController(getContext()));
        mPages.put(Pager.DRUG, new DrugController(getContext()));
        mPages.put(Pager.CHECK, new CheckController(getContext()));
        mPages.put(Pager.OTHER_ADVISE, new OtherAdviseController(getContext()));

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
        int space = QMUIDisplayHelper.dp2px(getContext(), 42);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);  // 超出自动适应，且可滚动
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setPadding(space, 0, space, 0);
        QMUITabSegment.Tab mDiagnosisAdvise = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_doctor_selected),
                "诊断医嘱", false
        );

        QMUITabSegment.Tab mDrugAdvise = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_drug_selected),
                "药品医嘱", false
        );

        QMUITabSegment.Tab mCheckAdvise = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_check_selected),
                "检查医嘱", false
        );

        QMUITabSegment.Tab mOtherAdvise = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_category),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_category_selected),
                "其他医嘱", false
        );

        mTabSegment.addTab(mDiagnosisAdvise)
                .addTab(mDrugAdvise)
                .addTab(mCheckAdvise)
                .addTab(mOtherAdvise);
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("病历详情");
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        // 刷新所有controller下的数据
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electronic_case_detail;
    }

    @Override
    protected void initLastCustom() {
        initTabs();
        initPagers();
    }
}
