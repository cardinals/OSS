package com.nobitastudio.oss.base.controller;

import android.content.Context;
import android.view.LayoutInflater;

import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.base.inter.HttpHandler;
import com.nobitastudio.oss.model.dto.GetParam;
import com.nobitastudio.oss.model.dto.ReflectStrategy;
import com.nobitastudio.oss.util.OkHttpUtil;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/03/29 16:05
 * @description 这是类描述
 */
public abstract class BaseController extends QMUIWindowInsetLayout
        implements HttpHandler {

    protected Context mContext;
    protected ControllerClickHandler mHandler;
    protected TipDialogHelper mTipDialogHelper;

    @Override
    public OkHttpUtil.NetworkUnavailableHandler getNetworkUnavailableHandler() {
        return () -> mTipDialogHelper.showInfoTipDialog("当前网络不可用", BaseController.this);
    }

    @Override
    public OkHttpUtil.ConnectFailHandler getConnectFailHandler() {
        return (call, e) -> mTipDialogHelper.showInfoTipDialog("服务器开小差了,请稍后再试", BaseController.this);
    }

    @Override
    public OkHttpUtil.ErrorHandler getErrorHandler() {
        return (call, response) -> mTipDialogHelper.showErrorTipDialog("服务器发生错误,请联系系统管理员", BaseController.this);
    }

    // ================ view
    protected abstract int getLayoutId();

    private void init() {
        initBase(); // 基础的初始化
        initLast(); // 自定义初始化

    }

    private void initBase() {
        mTipDialogHelper = new TipDialogHelper(mContext);
    }

    public abstract void initLast();

    // todo 测试及其兼容使用
    public BaseController(Context context) {
        this(context, null);
    }

    // ================= dialog
    protected void showNetworkLoadingTipDialog(String detailMsg) {
        mTipDialogHelper.showNetworkLoadingTipDialog(detailMsg);
    }

    protected void showNetworkLoadingTipDialog(String detailMsg, long delayTime) {
        mTipDialogHelper.showNetworkLoadingTipDialog(detailMsg, delayTime, BaseController.this);
    }

    protected void closeTipDialog() {
        mTipDialogHelper.closeTipDialog();
    }

    protected void showErrorTipDialog(String errorMsg, Long delayMills) {
        mTipDialogHelper.showErrorTipDialog(errorMsg, delayMills, BaseController.this);
    }

    protected void showInfoTipDialog(String infoMsg) {
        mTipDialogHelper.showInfoTipDialog(infoMsg, BaseController.this);
    }

    protected void showInfoTipDialog(String infoMsg, Long delayMills) {
        mTipDialogHelper.showInfoTipDialog(infoMsg, delayMills, BaseController.this);
    }

    protected void showSuccessTipDialog(String successMsg, Long delayMills) {
        mTipDialogHelper.showSuccessTipDialog(successMsg, delayMills, BaseController.this);
    }

    // ================== http util
    // get请求
    public <T> Call getAsyn(List<String> restParams, List<GetParam> getParams, ReflectStrategy<T> reflectStrategy,
                            OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.GET,
                Boolean.TRUE, restParams, getParams, null, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // 只调用 .不需要处理返回结果
    public Call getAsyn(List<String> restParams, List<GetParam> getParams) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.GET,
                Boolean.FALSE, restParams, getParams, null, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // delete请求
    public <T> Call deleteAsyn(List<String> restParams, List<GetParam> getParams, ReflectStrategy<T> reflectStrategy,
                               OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.DELETE,
                Boolean.TRUE, restParams, getParams, null, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // 只调用 .不需要处理返回结果
    public Call deleteAsyn(List<String> restParams, List<GetParam> getParams) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.DELETE,
                Boolean.FALSE, restParams, getParams, null, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // post 请求
    public <T> Call postAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody, ReflectStrategy<T> reflectStrategy,
                             OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.POST,
                Boolean.TRUE, restParams, getParams, requestBody, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // post 请求 不处理返回结果
    public <T> Call postAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.POST,
                Boolean.FALSE, restParams, getParams, requestBody, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // put 请求
    public <T> Call putAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody, ReflectStrategy<T> reflectStrategy,
                            OkHttpUtil.SuccessHandler<T> successHandler, OkHttpUtil.FailHandler<T> failureHandler) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.PUT,
                Boolean.TRUE, restParams, getParams, requestBody, reflectStrategy,
                getNetworkUnavailableHandler(), getConnectFailHandler(), successHandler, failureHandler, getErrorHandler());
    }

    // put 请求 不处理返回结果
    public <T> Call putAsyn(List<String> restParams, List<GetParam> getParams, Object requestBody) {
        return OkHttpUtil.asyn(OkHttpUtil.METHOD.PUT,
                Boolean.FALSE, restParams, getParams, requestBody, null,
                getNetworkUnavailableHandler(), null, null, null, null);
    }

    // 在主线程上运行
    protected void runOnUiThread(Runnable action) {
        this.post(action);
    }

    public BaseController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        ButterKnife.bind(this);
        init();
    }
}
