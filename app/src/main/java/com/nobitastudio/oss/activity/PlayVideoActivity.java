package com.nobitastudio.oss.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.activity.BaseActivity;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.model.entity.HealthArticle;
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

        String url = ConstantContainer.OSS_SERVER_RUNTIME + "/video/test-video.mp4"; // 视频源.采用测试视频

        mVideoPlayer.setUp(url, true, NormalContainer.get(NormalContainer.SELECTED_DOCTOR_LECTURE, HealthArticle.class).getTitle()); // 设置title
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.bg_video_test);
        mVideoPlayer.setThumbImageView(imageView);

        //增加title  设置padding
        mVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        mVideoPlayer.getTitleTextView().setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(getContext()), 0, 0);
        mVideoPlayer.getBackButton().setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(getContext()), 0, 0);

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
        NormalContainer.put(NormalContainer.SELECTED_ACTIVITY, this);
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
