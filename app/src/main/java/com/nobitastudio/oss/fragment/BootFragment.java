package com.nobitastudio.oss.fragment;

import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.controller.boot.LastController;
import com.nobitastudio.oss.controller.boot.SimpleController;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/05 12:19
 * @description 用户首次进入时进行引导
 */
public class BootFragment extends BaseFragment {

    @BindView(R.id.ultraview_pager)
    UltraViewPager mBootUltraViewPager;

    BootUltraPagerAdapter mBootUltraPagerAdapter;
    List<View> views;  // 有哪些view

    public class BootUltraPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View root = views.get(position);
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void init() {
        View oneController = new SimpleController(getContext(),R.drawable.ic_lock);
        View twoController = new SimpleController(getContext(),R.drawable.ic_lock);
        View threeController = new SimpleController(getContext(),R.drawable.ic_lock);
        View fourController = new LastController(getContext());
        views = Arrays.asList(oneController, twoController, threeController, fourController);
        mBootUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mBootUltraPagerAdapter = new BootUltraPagerAdapter();
        mBootUltraViewPager.setAdapter(mBootUltraPagerAdapter);
        mBootUltraViewPager.setInfiniteLoop(false);

        //init indicator.
        mBootUltraViewPager.initIndicator();

        //set style of indicators
        mBootUltraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(R.color.app_color_blue)
                .setNormalColor(R.color.qmui_config_color_white)
                .setMargin(0, 0, 0, 100)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

        //set the alignment
        mBootUltraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        mBootUltraViewPager.getIndicator().build();
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_boot, null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }
}
