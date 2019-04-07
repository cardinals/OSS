package com.nobitastudio.oss.base.helper;

import android.view.View;

import com.qmuiteam.qmui.widget.QMUIEmptyView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/19 11:26
 * @description
 */
public class EmptyViewHelper {

    View.OnClickListener mListener;

    public EmptyViewHelper(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    public void showLoadingEmptyView(QMUIEmptyView mEmptyView, String detailMsg) {
        mEmptyView.show(true, null, detailMsg == null ? "正在加载" : detailMsg, null, null);
    }

    public void showLoadingFailEmptyView(QMUIEmptyView mEmptyView, String titleText, String buttonText) {
        mEmptyView.show(false, titleText == null ? "加载失败" : titleText, null, buttonText == null ? "点击重试" : buttonText, mListener);
    }

    public void showSimpleTextEmptyView(QMUIEmptyView mEmptyView, String titleText) {
        mEmptyView.show(false, titleText == null ? "加载失败" : titleText, null, null,null);
    }

    public void closeLoadingEmptyView(QMUIEmptyView mEmptyView) {
        if (mEmptyView.isShowing()) {
            mEmptyView.hide();
        }
    }
}
