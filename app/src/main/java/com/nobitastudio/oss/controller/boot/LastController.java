package com.nobitastudio.oss.controller.boot;

import android.content.Context;
import android.view.LayoutInflater;

import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class LastController extends QMUIWindowInsetLayout {



    private void init() {

    }

    public LastController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_last, this);
        ButterKnife.bind(this);
        init();
    }
}
