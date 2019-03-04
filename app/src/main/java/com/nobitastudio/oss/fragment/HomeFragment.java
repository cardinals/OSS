package com.nobitastudio.oss.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.controller.home.HomeController;
import com.nobitastudio.oss.controller.home.InfoController;
import com.nobitastudio.oss.controller.home.MineController;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class HomeFragment extends BaseFragment {

    /**
     * 初始化有哪些 pager
     */
    enum Pager {
        HOME, INFO, MINE;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return HOME;
                case 1:
                    return INFO;
                case 2:
                    return MINE;
                default:
                    return HOME;
            }
        }
    }

    @BindView(R.id.fragment_pager)
    ViewPager mViewPager;
    @BindView(R.id.fragment_tabs)
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

    /**
     * 初始化pagers  分别为 医院主页,消息,我的
     */
    private void initPagers() {
        mPages = new HashMap<>();
        ControllerClickHandler mHandler = new ControllerClickHandler() {
            @Override
            public void startFragment(BaseFragment target) {
                HomeFragment.this.startFragment(target);
            }

            @Override
            public void startFragmentAndDestroyCurrent(BaseFragment targetFragment) {
                HomeFragment.this.startFragmentAndDestroyCurrent(targetFragment);
            }
        };
        mPages.put(Pager.HOME, new HomeController(getContext(), mHandler));
        mPages.put(Pager.INFO, new InfoController(getContext(), mHandler));
        mPages.put(Pager.MINE, new MineController(getContext(), mHandler));
        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        QMUITabSegment.Tab home = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_home),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_home_selected),
                getResources().getString(R.string.home), false
        );

        QMUITabSegment.Tab info = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_notification),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_notification_selected),
                getResources().getString(R.string.msg), false
        );
        QMUITabSegment.Tab mine = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_mine),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_mine_selected),
                getResources().getString(R.string.mine), false
        );
        mTabSegment.addTab(home)
                .addTab(info)
                .addTab(mine);
//        mTabSegment.setDefaultTabIconPosition(QMUITabSegment.ICON_POSITION_BOTTOM);
//        // 如果你的 icon 显示大小和实际大小不吻合:
//        // 1. 设置icon 的 bounds
//        // 2. Tab 使用拥有5个参数的构造器
//        // 3. 最后一个参数（setIntrinsicSize）设置为false
//        int iconShowSize = QMUIDisplayHelper.dp2px(getContext(), 20);
//        Drawable normalDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component);
//        normalDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//        Drawable selectDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component_selected);
//
//        selectDrawable.setBounds(0, 0, iconShowSize, iconShowSize);
//
//        QMUITabSegment.Tab home = new QMUITabSegment.Tab(
//                normalDrawable,
//                normalDrawable,
//                "Components", false, false
//        );
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected View onCreateView() {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, frameLayout);
        initTabs();
        initPagers();
        return frameLayout;
    }
}
