package com.nobitastudio.oss.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.activity.BaseActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nobitastudio.oss.OSSApplication.getContext;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/12 13:23
 * @description 播放视频activity
 */
public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer mVideoPlayer;

    OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_playvideo, null);
        ButterKnife.bind(this, root);
        setContentView(root);
        init();
    }

    private void init() {

        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        mVideoPlayer.setUp(url, true, "这是title");
        // 设置title
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.bg_video_test);
        mVideoPlayer.setThumbImageView(imageView);

        //增加title  设置padding
        mVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mVideoPlayer.getTitleTextView().setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(getContext()),0,0);
        mVideoPlayer.getBackButton().setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(getContext()),0,0);

        //设置旋转
        mOrientationUtils = new OrientationUtils(this, mVideoPlayer);
        mVideoPlayer.getFullscreenButton().setOnClickListener(v -> mOrientationUtils.resolveByClick());
        //设置滑动
        mVideoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        mVideoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
        mVideoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (mOrientationUtils != null)
            mOrientationUtils.releaseListener();
    }

    @Override
    protected void doOnBackPressed() {
        //先返回正常状态
        if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mVideoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        mVideoPlayer.setVideoAllCallBack(null);
        super.doOnBackPressed();
    }

}
