package com.nobitastudio.oss.controller.boot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.old.LoginOldFragment;
import com.qmuiteam.qmui.util.QMUIDirection;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class LastController extends QMUIWindowInsetLayout {

    @BindView(R.id.linearlayout)
    LinearLayout mLinearLayout;

    ControllerClickHandler mHandler;

    @OnClick({R.id.login_button, R.id.enroll_button})
    void onClick(View v) {
        // 记录点击事件.下次不再进入Boot Fragment
        switch (v.getId()) {
            case R.id.login_button:
                mHandler.startFragment(new LoginOldFragment());
                break;
            case R.id.enroll_button:
                // 注册
                mHandler.startFragment(new HomeFragment());
                break;
        }
    }

    public LastController(Context context, int bootImageRes, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_last, this);
        this.setBackground(getResources().getDrawable(bootImageRes, null));
        ButterKnife.bind(this);
    }

    // 滑动出现
    public void SlideIn() {
        if (mLinearLayout.getVisibility() == View.GONE) {
            QMUIViewHelper.slideIn(mLinearLayout, 1000, null, true, QMUIDirection.BOTTOM_TO_TOP);
        }
    }

    // 滑动消失
    public void SlideOut() {
        if (mLinearLayout.getVisibility() != View.GONE) {
            QMUIViewHelper.slideOut(mLinearLayout, 500, null, true, QMUIDirection.TOP_TO_BOTTOM);
        }
    }

}
