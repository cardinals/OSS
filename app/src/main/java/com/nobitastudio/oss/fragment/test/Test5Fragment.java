package com.nobitastudio.oss.fragment.test;

import android.view.View;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.home.HomeFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.model.entity.User;

import java.util.Arrays;

import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入需要修改密码的手机号
 */
public class Test5Fragment extends StandardWithTobBarLayoutFragment {

    @OnClick({R.id.next_step_button, R.id.next_step2_button})
    void onClick(View v) {
        startFragment(new Test6Fragment());
    }

    @Override
    protected void initTopBar() {
        if (isSpecialTopBar()) {
            // 特殊topbar 特殊处理
            handleSpecialTopBar();
        } else {
            // 正常
            if (canDragBack()) {
                mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
            }
            mTopBar.setBackgroundDividerEnabled(topBarHavDivider());
            mTopBar.setTitle(getTopBarTitle());
            if (topBarIsTransparent()) {
                mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent, null));
            }
            initTopBarLast();
        }
    }

    @Override
    protected String getTopBarTitle() {
        return "绑定手机号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test4;
    }

    @Override
    protected void initLastCustom() {

    }
}
