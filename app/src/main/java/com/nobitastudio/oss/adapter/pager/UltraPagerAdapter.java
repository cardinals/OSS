package com.nobitastudio.oss.adapter.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.base.lab.fragment.QDWebViewFixFragment;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.entity.HealthArticle;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/23 18:08
 * @description 医院活动.健康资讯轮播图适配器  //数据已过滤
 */
public class UltraPagerAdapter extends PagerAdapter {

    Context mContext;
    List<HealthArticle> mHealthArticles;

    ControllerClickHandler mHandler;

    public UltraPagerAdapter(Context mContext, List<HealthArticle> mHealthArticles) {
        this.mContext = mContext;
        this.mHealthArticles = mHealthArticles;
    }

    @Override
    public int getCount() {
        return mHealthArticles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_item, null);
        ImageView imageView = root.findViewById(R.id.imageview);
        HealthArticle selectedHospitalActivity = mHealthArticles.get(position);
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + selectedHospitalActivity.getIconUrl()).into(imageView);
        container.addView(root);
        root.setOnClickListener((view) -> {
            // 存参数
            NormalContainer.put(NormalContainer.SELECTED_HEALTH_ARTICLE,selectedHospitalActivity);
            Toasty.info(mContext,"id:" + selectedHospitalActivity.getId() + ",url：" + selectedHospitalActivity.getUrl()).show();
            mHandler.startFragment(new QDWebViewFixFragment());
        });
        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public UltraPagerAdapter setControllerClickHandler(ControllerClickHandler mHandler) {
        this.mHandler = mHandler;
        return this;
    }
}
