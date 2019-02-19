package com.nobitastudio.oss.base.helper;

import android.view.View;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/18 18:04
 * @description
 */
public class ViewHelper {
    /**
     * 锁定所有视图不可选定
     * @param views
     */
    public void lockView(View... views) {
        for (View view : views) {
            view.setClickable(false);
        }
    }

    /**
     * 解锁视图
     * @param views
     */
    public void unlockView(View... views) {
        for (View view : views) {
            view.setClickable(true);
        }
    }
}
