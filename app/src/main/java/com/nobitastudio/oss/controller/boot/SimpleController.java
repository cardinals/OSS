package com.nobitastudio.oss.controller.boot;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description 简单的图片显示
 */
public class SimpleController extends QMUIWindowInsetLayout {

    public SimpleController(Context context, int bootImageRes) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_boot_simple, this);
        this.setBackground(getResources().getDrawable(bootImageRes, null));
    }
}
