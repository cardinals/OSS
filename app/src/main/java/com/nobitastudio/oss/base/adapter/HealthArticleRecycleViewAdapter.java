package com.nobitastudio.oss.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.model.entity.HealthArticle;
import com.nobitastudio.oss.util.CommonUtil;
import com.nobitastudio.oss.util.DateUtil;

import java.util.List;

public class HealthArticleRecycleViewAdapter extends RecyclerView.Adapter<HealthArticleRecycleViewAdapter.ViewHolder> {

    List<HealthArticle> healthArticles;
    Context mContext;
    AppCompatActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView titleTextView;
        TextView typeTextView;
        TextView publishTimeTextView;
        ImageView coverImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            this.titleTextView = itemView.findViewById(R.id.title_textView);
            this.typeTextView = itemView.findViewById(R.id.type_textView);
            this.publishTimeTextView = itemView.findViewById(R.id.publish_time_textView);
            this.coverImageView = itemView.findViewById(R.id.cover_imageView);
        }
    }

    public HealthArticleRecycleViewAdapter(Context context, List<HealthArticle> healthArticles) {
        this.healthArticles = healthArticles;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_health_article, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //enterHealthNewsDetailsActivity(position);
                ToastUtils.showShort(healthArticles.get(position).getUrl());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthArticle healthArticle = healthArticles.get(position);
        Glide.with(mContext).load(R.drawable.hospital_trademark).into(holder.coverImageView);
        holder.titleTextView.setText(healthArticle.getTitle());
        holder.typeTextView.setText(healthArticle.getType().name());
        holder.publishTimeTextView.setText(CommonUtil.handleHealthNewsPublishTime(DateUtil.formatLocalDateTimeToSimpleString(healthArticle.getPublishTime())));
    }

    @Override
    public int getItemCount() {
        return healthArticles.size();
    }

}
