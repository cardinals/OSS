package com.nobitastudio.oss.fragment.standard;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.fragment.BaseFragment;
import com.nobitastudio.oss.base.helper.BottomSheetHelper;
import com.nobitastudio.oss.base.helper.DialogHelper;
import com.nobitastudio.oss.base.helper.EmptyViewHelper;
import com.nobitastudio.oss.base.helper.PopViewHelper;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.helper.TipDialogHelper;
import com.nobitastudio.oss.base.helper.ViewHelper;
import com.nobitastudio.oss.util.DateUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public abstract class StandardFragment extends BaseFragment {

    @BindView(R.id.pull_to_refresh)
    protected QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.emptyView)
    protected QMUIEmptyView mEmptyView;

    Gson mGson;
    OkHttpClient mOkHttpClient;

    BottomSheetHelper mBottomSheetHelper;
    DialogHelper mDialogHelper;
    EmptyViewHelper mEmptyViewHelper;
    PopViewHelper mPopViewHelper;
    SolidImageHelper mSolidImageHelper;
    TipDialogHelper mTipDialogHelper;
    ViewHelper mViewHelper;
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    // SLIDE_TRANSITION_CONFIG  左右滑动
    // SCALE_TRANSITION_CONFIG  从中间出
    protected static final TransitionConfig animConfig = SLIDE_TRANSITION_CONFIG;  // 默认左右滑动

    Boolean inited = Boolean.FALSE;   // 初始化过,便不再需要初始化

    ExecutorService executorService;  // 线程池 ,暂不使用

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return animConfig;
    }

    // 完成初始化操作
    @Override
    public void onResume() {
        if (!inited) {
            // 初始化操作
            init();
        }
        super.onResume();
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        ButterKnife.bind(this, root);
        return root;
    }

    public StandardFragment() {
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
    }

    // 初始化基本变量
    protected void initBase() {
        mBottomSheetHelper = new BottomSheetHelper(getContext());
        mDialogHelper = new DialogHelper(getContext());
        mEmptyViewHelper = new EmptyViewHelper(getEmptyViewRetryButtonListener());
        mPopViewHelper = new PopViewHelper(getContext());
        mSolidImageHelper = new SolidImageHelper(getContext());
        mTipDialogHelper = new TipDialogHelper(getContext());
        mViewHelper = new ViewHelper();
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(getContext());

//        executorService = Executors.newCachedThreadPool();
    }

    // 初始化方法
    private void init() {
        initFirstCustom();
        initBase();
        initMiddleCustom();
        initTopBar();
        initRefreshLayout();
        initLastCustom();
        inited = Boolean.TRUE;
    }

    // 当需要在最开始进行初始化，重写该方法
    protected void initFirstCustom() {
        // do nothing 
    }

    // 当需要在中途进行初始化时，重写该方法
    protected void initMiddleCustom() {
        // do nothing
    }

    //初始化、是否启用 refreshLayout 默认不启用
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(Boolean.FALSE);
    }

    // emptyView 展示加载失败时,点击重试的 listener 默认不重写 ,当需要时进行重写
    protected View.OnClickListener getEmptyViewRetryButtonListener() {
        return view -> ToastUtils.showShort("尚未初始化该点击事件,请重写getEmptyViewRetryButtonListener");
    }

    // ==================== topbar
    //初始化topbar
    protected abstract void initTopBar();

    // topbar 是否是透明的.默认是不透明的
    protected Boolean topBarIsTransparent() {
        return Boolean.FALSE;
    }

    // tob是否具有分割线 默认有
    protected Boolean topBarHavDivider() {
        return Boolean.TRUE;
    }

    // 获取topbar 的title
    // todo 更改为返回在values的id.
    protected abstract String getTopBarTitle();

    // 除去基本操作,最后还需要初始化topbar做的事情,比如增加右侧菜单
    protected void initTopBarLast() {
        // 默认没有其他操作
        return;
    }

    // 是否是特殊的TopBar.采取特殊的处理策略.医生详情界面的topbar为特殊 topbar.默认不是
    protected Boolean isSpecialTopBar() {
        return Boolean.FALSE;
    }

    // 当topbar 是特殊类型的时候.进行特殊处理
    protected void handleSpecialTopBar() {
        // 特殊处理
    }

    // ================ view
    protected abstract int getLayoutId();

    // 用户最后进行初始化，进行大部分的初始化工作
    protected abstract void initLastCustom();

    // =============================  EmptyView

    protected void showLoadingEmptyView(String detailMsg) {
        mEmptyViewHelper.showLoadingEmptyView(mEmptyView, detailMsg);
    }

    protected void showLoadingFailEmptyView(String titleText, String buttonText) {
        mEmptyViewHelper.showLoadingFailEmptyView(mEmptyView, titleText, buttonText);
    }

    protected void closeLoadingEmptyView() {
        mEmptyViewHelper.closeLoadingEmptyView(mEmptyView);
    }

    // ===================================tipDialog

    protected QMUITipDialog closeTipDialog() {
        return mTipDialogHelper.closeTipDialog();
    }

    protected QMUITipDialog showNetworkLoadingTipDialog(String detailMsg) {
        return mTipDialogHelper.showNetworkLoadingTipDialog(detailMsg);
    }

    protected QMUITipDialog showErrorTipDialog(String errorMsg) {
        return mTipDialogHelper.showErrorTipDialog(errorMsg, mEmptyView);
    }

    protected QMUITipDialog showInfoTipDialog(String infoMsg) {
        return mTipDialogHelper.showInfoTipDialog(infoMsg, mEmptyView);
    }

    protected QMUITipDialog showSuccessTipDialog(String successMsg) {
        return mTipDialogHelper.showSuccessTipDialog(successMsg, mEmptyView);
    }

    protected QMUITipDialog showErrorTipDialog(String errorMsg, Long delayMills) {
        return mTipDialogHelper.showErrorTipDialog(errorMsg, delayMills, mEmptyView);
    }

    protected QMUITipDialog showInfoTipDialog(String infoMsg, Long delayMills) {
        return mTipDialogHelper.showInfoTipDialog(infoMsg, delayMills, mEmptyView);
    }

    protected QMUITipDialog showSuccessTipDialog(String successMsg, Long delayMills) {
        return mTipDialogHelper.showSuccessTipDialog(successMsg, delayMills, mEmptyView);
    }

    // ================================ 不同类型的对话框 dialog

    protected QMUIDialog showMessagePositiveDialog(String title, String content,
                                                   String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                   String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        return mDialogHelper.showMessagePositiveDialog(title, content, cancelMsg, cancelListener, confirmMsg, confirmListener);
    }

    protected QMUIDialog showMessageNegativeDialog(String title, String content,
                                                   String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                   String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        return mDialogHelper.showMessageNegativeDialog(title, content, cancelMsg, cancelListener, confirmMsg, confirmListener);
    }

    protected QMUIDialog showLongMessageDialog(String title, String content,
                                               String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                               String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        return mDialogHelper.showLongMessageDialog(title, content, cancelMsg, cancelListener, confirmMsg, confirmListener);
    }

    protected QMUIDialog showMenuDialog(List<String> items, DialogInterface.OnClickListener itemClickListener) {
        return mDialogHelper.showMenuDialog(items, itemClickListener);
    }

    protected QMUIDialog showConfirmMessageDialog(String title, String content,
                                                  String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                  String confirmMsg, QMUIDialogAction.ActionListener confirmListener,
                                                  Boolean checked) {
        return mDialogHelper.showConfirmMessageDialog(title, content, cancelMsg, cancelListener, confirmMsg, confirmListener, checked);
    }

    protected QMUIDialog showSingleChoiceDialog(DialogInterface.OnClickListener listener, List<String> items, int checkedIndex) {
        return mDialogHelper.showSingleChoiceDialog(items, listener, checkedIndex);
    }

    protected QMUIDialog showMultiChoiceDialog(String title,
                                               String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                               String confirmMsg, DialogHelper.MultiChoiceDialogConfirmListener confirmListener,
                                               List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        return mDialogHelper.showMultiChoiceDialog(title,
                cancelMsg, cancelListener,
                confirmMsg, confirmListener,
                items, checkedItems, itemClickListener);
    }

    protected QMUIDialog showNumerousMultiChoiceDialog(String title,
                                                       String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                       String confirmMsg, DialogHelper.MultiChoiceDialogConfirmListener confirmListener,
                                                       List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        return mDialogHelper.showNumerousMultiChoiceDialog(title,
                cancelMsg, cancelListener,
                confirmMsg, confirmListener,
                items, checkedItems, itemClickListener);
    }

    protected QMUIDialog showEditTextDialog(String title, String placeHolder,
                                            String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                            String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        return mDialogHelper.showEditTextDialog(title, placeHolder, cancelMsg, cancelListener, confirmMsg, confirmListener);
    }

    protected QMUIDialog showAutoDialog(String hintText, String content,
                                        String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                        String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        return mDialogHelper.showAutoDialog(hintText, content, cancelMsg, cancelListener, confirmMsg, confirmListener);
    }

    // ============================== 展示 popView

    protected void popViewDismiss() {
        mPopViewHelper.popViewDismiss();
    }

    protected QMUIPopup showNormalPopView(View v, String content, Integer width, PopupWindow.OnDismissListener popViewDismissListener) {
        return mPopViewHelper.showNormalPopView(v, content, width, popViewDismissListener);
    }

    protected QMUIListPopup showListPopView(View v, List<String> items,
                                            AdapterView.OnItemClickListener itemClickListener, PopupWindow.OnDismissListener popViewDismissListener) {
        return showListPopView(v, items, 120, 160, itemClickListener, popViewDismissListener);
    }

    protected QMUIListPopup showListPopView(View v, List<String> items, Integer width, Integer height,
                                            AdapterView.OnItemClickListener itemClickListener, PopupWindow.OnDismissListener popViewDismissListener) {
        return mPopViewHelper.showListPopView(v, items, width, height, itemClickListener, popViewDismissListener);
    }

    // ============================== bottomSheet

    protected QMUIBottomSheet showSimpleBottomSheetList(List<String> items, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener itemClickListener) {
        return mBottomSheetHelper.showSimpleBottomSheetList(items, itemClickListener);
    }

    protected QMUIBottomSheet showSimpleBottomSheetGrid(List<Integer> mipmaps, List<String> titles, List<Integer> tags, QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener itemClickListener) {
        return mBottomSheetHelper.showSimpleBottomSheetGrid(mipmaps, titles, tags, itemClickListener);
    }

    // ============================= solidImgeView
    protected void initSolidImage(ImageView... imageViews) {
        mSolidImageHelper.initSolidImage(imageViews);
    }

    // ============================= 版权信息

    /**
     * 初始化版权信息
     */
    protected void initCopyRight(TextView... mCopyrightTextView) {
        for (TextView textView : mCopyrightTextView) {
            textView.setText(String.format(getResources().getString(R.string.about_copyright), DateUtil.getCurrentYear()));
        }
    }

    // ======================== viewTools

    public void lockView(View... views) {
        mViewHelper.lockView(views);
    }

    public void unlockView(View... views) {
        mViewHelper.unlockView(views);
    }

    // ========================  controller handler


    // ========================= QMUILinearLayout
    public void initQMUILinearLayout(View v) {
        if (v instanceof QMUILinearLayout) {
            mQMUILinearLayoutHelper.init(v);
        }
    }

    public void initQMUILinearLayout(View v, float alpha, int elevation, int radius) {
        if (v instanceof QMUILinearLayout) {
            mQMUILinearLayoutHelper.init(v, alpha, elevation, radius);
        }
    }

}
