package com.nobitastudio.oss.controller.electroniccase;

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
public class OtherAdviseController extends QMUIWindowInsetLayout {

    protected void init() {

    }

    public OtherAdviseController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_electronic_case_detail_other, this);
        ButterKnife.bind(this);
        init();
    }

}
