package com.nobitastudio.oss.base.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/18 17:55
 * @description tipDialog 控制
 */
public class TipDialogHelper {

    QMUITipDialog mQmuiTipDialog;

    long mDefaultTipDialogShowTime = 1500l;

    Context mContext;

    public TipDialogHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void closeTipDialog() {
        if (mQmuiTipDialog != null && mQmuiTipDialog.isShowing()) {
            mQmuiTipDialog.hide();
        }
    }

    public void showNetworkLoadingTipDialog(String detailMsg) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(detailMsg == null ? "正在加载" : detailMsg)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
    }

    public void showErrorTipDialog(String errorMsg, View viewToPostDelayed) {
        showErrorTipDialog(errorMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
    }

    public void showInfoTipDialog(String infoMsg, View viewToPostDelayed) {
        showInfoTipDialog(infoMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
    }

    public void showSuccessTipDialog(String successMsg, View viewToPostDelayed) {
        showSuccessTipDialog(successMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
    }

    public void showErrorTipDialog(String errorMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(errorMsg == null ? "发送失败" : errorMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    public void showInfoTipDialog(String infoMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(infoMsg == null ? "请勿重复操作" : infoMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    public void showSuccessTipDialog(String successMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(successMsg == null ? "发送成功" : successMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }
}
