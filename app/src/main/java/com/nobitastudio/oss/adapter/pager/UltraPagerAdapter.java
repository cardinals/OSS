package com.nobitastudio.oss.adapter.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.model.entity.HealthArticle;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 18:08
 * @description 医院活动.健康资讯轮播图适配器  //数据已过滤
 */
public class UltraPagerAdapter extends PagerAdapter {

    Context mContext;
    List<HealthArticle> healthArticles;

    public UltraPagerAdapter(Context mContext, List<HealthArticle> healthArticles) {
        this.mContext = mContext;
        this.healthArticles = healthArticles;
    }

    @Override
    public int getCount() {
        return healthArticles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_item, null);
        ImageView imageView = root.findViewById(R.id.imageview);
        Glide.with(mContext).load(healthArticles.get(position).getIconUrl()).into(imageView);
        container.addView(root);
        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
