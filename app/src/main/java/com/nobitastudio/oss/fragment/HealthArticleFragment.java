package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.controller.home.HomeController;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.CommonUtil;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class HealthArticleFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.ultraview_pager)
    UltraViewPager mUltraViewPager;

    HealthArticleUltraPagerAdapter mHealthArticleUltraPagerAdapter;

    private static float mShadowAlpha = 1.0f;
    private static int mShadowElevationDp = 10;
    private static int mRadius = 15;

    // 健康头条
    public static class HeadlineRecycleViewAdapter extends BaseRecyclerViewAdapter<HealthArticle> {

        public HeadlineRecycleViewAdapter(Context ctx, List<HealthArticle> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_headline;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
            Glide.with(mContext).load(R.drawable.bg_hospital_trademark).into(holder.getImageView(R.id.cover_imageView));
//            holder.setText(R.id.title_textView, item.getTitle());
//            holder.setText(R.id.type_textView, item.getType().name());
//            holder.setText(R.id.publish_time_textView, CommonUtil.handleHealthNewsPublishTime(DateUtil.formatLocalDateTimeToSimpleString(item.getPublishTime())));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    // 名师讲堂
    public static class DoctorLectureRecyclerViewAdapter extends BaseRecyclerViewAdapter<HealthArticle> {

        public DoctorLectureRecyclerViewAdapter(Context ctx, List<HealthArticle> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_doctor_lecture;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
            ((QMUILinearLayout) holder.getView(R.id.doctor_lecture_linearlayout)).setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    public class HealthArticleUltraPagerAdapter extends PagerAdapter {

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
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t1).into(imageView);
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
    }

    /**
     * 初始化医院活动mUltraPager
     */
    private void initUltraViewPager() {
        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mHealthArticleUltraPagerAdapter = new HealthArticleUltraPagerAdapter();
        mUltraViewPager.setAdapter(mHealthArticleUltraPagerAdapter);
        mUltraViewPager.setInfiniteLoop(true);
        mUltraViewPager.setAutoScroll(4000);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("健康资讯");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_health_article;
    }

    @Override
    protected void initLastCustom() {
        initUltraViewPager();
    }


}
