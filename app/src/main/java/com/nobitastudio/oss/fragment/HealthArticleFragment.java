package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.CommonUtil;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class HealthArticleFragment extends StandardWithTobBarLayoutFragment {

    public static class HealthArticleRecycleViewAdapter extends BaseRecyclerViewAdapter<HealthArticle> {

        public HealthArticleRecycleViewAdapter(Context ctx, List<HealthArticle> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_health_article;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, HealthArticle item) {
            Glide.with(mContext).load(R.drawable.bg_hospital_trademark).into(holder.getImageView(R.id.cover_imageView));
            holder.setText(R.id.title_textView, item.getTitle());
            holder.setText(R.id.type_textView, item.getType().name());
            holder.setText(R.id.publish_time_textView, CommonUtil.handleHealthNewsPublishTime(DateUtil.formatLocalDateTimeToSimpleString(item.getPublishTime())));
        }
    }

    @BindView(R.id.solidImage)
    ImageView solidImage;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("title");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_standard;
    }

    @Override
    protected void initLastCustom() {
        initSolidImage(solidImage);
    }
}
