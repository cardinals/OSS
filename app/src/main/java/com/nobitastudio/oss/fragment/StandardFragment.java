package com.nobitastudio.oss.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class StandardFragment extends BaseFragment {

    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;

    QMUITipDialog mQmuiTipDialog;
    QMUIPopup mNormalPopup;
    QMUIListPopup mListPopup;

    Gson mGson;
    OkHttpClient mOkHttpClient;

    int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    long mDefaultTipDialogShowTime = 1500l;

    public StandardFragment() {
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
    }

    /**
     * 初始化方法
     */
    protected abstract void init();

    /**
     * 初始化topbar
     */
    protected abstract void initTopBar();

    /**
     * 初始化、是否启用 refreshLayout
     */
    protected abstract void initRefreshLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * emptyView 展示加载失败时,点击重试的listener
     *
     * @return
     */
    protected abstract View.OnClickListener getEmptyViewRetryButtonListener();

    // =============================  EmptyView

    protected void showLoadingEmptyView(String detailMsg) {
        mEmptyView.show(true, null, detailMsg == null ? "正在加载" : detailMsg, null, null);
    }

    protected void showLoadingFailEmptyView(String titleText, String buttonText) {
        mEmptyView.show(false, titleText == null ? "加载失败" : titleText, null, buttonText == null ? "点击重试" : buttonText, getEmptyViewRetryButtonListener());
    }

    protected void closeLoadingEmptyView() {
        if (mEmptyView.isShowing()) {
            mEmptyView.hide();
        }
    }

    // ===================================tipDialog

    protected void closeTipDialog() {
        if (mQmuiTipDialog != null && mQmuiTipDialog.isShowing()) {
            mQmuiTipDialog.hide();
        }
    }

    protected void showNetworkLoadingTipDialog(String detailMsg) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(detailMsg == null ? "正在加载" : detailMsg)
                .create();
        mQmuiTipDialog.show();
        //ViewUtil.lockView(forgetPasswordTextView, enrollTextView, loginButton);
    }

    protected void showErrorTipDialog(String errorMsg) {
        showErrorTipDialog(errorMsg, mDefaultTipDialogShowTime);
    }

    protected void showInfoTipDialog(String infoMsg) {
        showInfoTipDialog(infoMsg, mDefaultTipDialogShowTime);
    }

    protected void showSuccessTipDialog(String successMsg) {
        showSuccessTipDialog(successMsg, mDefaultTipDialogShowTime);
    }

    protected void showErrorTipDialog(String errorMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(errorMsg == null ? "发送失败" : errorMsg)
                .create();
        mQmuiTipDialog.show();
        mEmptyView.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    protected void showInfoTipDialog(String infoMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(infoMsg == null ? "请勿重复操作" : infoMsg)
                .create();
        mQmuiTipDialog.show();
        mEmptyView.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    protected void showSuccessTipDialog(String successMsg, Long delayMills) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(successMsg == null ? "发送成功" : successMsg)
                .create();
        mQmuiTipDialog.show();
        mEmptyView.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
    }

    // ================================ 不同类型的对话框 dialog

    /**
     * 生成带取消以及确认的对话框，取消及其确认使用的默认的主题颜色(蓝色)，
     *
     * @param context         上下文
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    protected void showMessagePositiveDialog(Context context, String title, String content,
                                             String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                             String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 生成带取消以及删除的对话框，删除字体采用的红色字体，
     *
     * @param context         上下文
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    protected void showMessageNegativeDialog(Context context, String title, String content,
                                             String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                             String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(0, cancelMsg, QMUIDialogAction.ACTION_PROP_NEGATIVE, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * @param context         上下文
     * @param title           题目
     * @param content         很长的内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字  暂时未使用,可以传入null
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器  暂时未使用,客串日null
     */
    protected void showLongMessageDialog(Context context, String title, String content, String cancelMsg, String confirmMsg,
                                         QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancelMsg, cancelListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 菜单类型对话框
     *
     * @param context           上下文
     * @param itemClickListener 选中某一个时的监听器
     * @param items             可供选择的子项名称
     */
    protected void showMenuDialog(Context context, DialogInterface.OnClickListener itemClickListener, List<String> items) {
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(items.toArray(new String[0]), itemClickListener)
                .create(mCurrentDialogStyle).show();
    }


    /**
     * 带一个单选框的dialog
     *
     * @param context         上下文
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     * @param checked         默认是否选中
     */
    protected void showConfirmMessageDialog(Context context, String title, String content, String cancelMsg, String confirmMsg,
                                            QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener,
                                            Boolean checked) {
        new QMUIDialog.CheckBoxMessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .setChecked(checked == null ? Boolean.TRUE : checked)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 菜单对话框,默认有选中的项目
     *
     * @param context
     * @param listener
     * @param items
     * @param checkedIndex
     */
    protected void showSingleChoiceDialog(Context context, DialogInterface.OnClickListener listener, List<String> items, int checkedIndex) {
        new QMUIDialog.CheckableDialogBuilder(context)
                .setCheckedIndex(checkedIndex)
                .addItems(items.toArray(new String[0]), listener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 多选对话框,含有很多单选框,但不是最多
     *
     * @param context           上下文
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     */
    protected void showMultiChoiceDialog(Context context, String cancelMsg, String confirmMsg, DialogInterface.OnClickListener itemClickListener,
                                         QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener,
                                         List<String> items, int[] checkedItems) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener);
        builder.addAction(cancelMsg, cancelListener);
        builder.addAction(confirmMsg, confirmListener);
        builder.create(mCurrentDialogStyle).show();
    }

    /**
     * 带有很多的单选框的 dialog
     *
     * @param context           上下文
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     */
    protected void showNumerousMultiChoiceDialog(Context context, String cancelMsg, String confirmMsg, List<String> items, int[] checkedItems,
                                                 QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener,
                                                 DialogInterface.OnClickListener itemClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener);
        builder.addAction(cancelMsg, cancelListener);
        builder.addAction(confirmMsg, confirmListener);
        builder.create(mCurrentDialogStyle).show();
    }

    /**
     * 带editTextView 的dialog
     *
     * @param context         上下文
     * @param title           标题
     * @param placeHolder     EditTextView 的 hint
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    protected void showEditTextDialog(Context context, String title, String placeHolder, String cancelMsg, String confirmMsg,
                                      QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle(title)
                .setPlaceholder(placeHolder)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 高度自适应 带editTextView 的dialog
     *
     * @param context         上下文
     * @param hintText        EditTextView 的 hint
     * @param content         正文
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    protected void showAutoDialog(Context context, String hintText, String content, String cancelMsg, String confirmMsg,
                                  QMUIDialogAction.ActionListener cancelListener, QMUIDialogAction.ActionListener confirmListener) {
        QMAutoTestDialogBuilder autoTestDialogBuilder = (QMAutoTestDialogBuilder) new QMAutoTestDialogBuilder(context, hintText, content)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener);
        autoTestDialogBuilder.create(mCurrentDialogStyle).show();
        QMUIKeyboardHelper.showKeyboard(autoTestDialogBuilder.getEditText(), true);
    }

    class QMAutoTestDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
        private Context mContext;
        private EditText mEditText;
        private String hintText;
        private String content;

        public QMAutoTestDialogBuilder(Context context) {
            this(context, "请输入", "内容正文");
        }

        public QMAutoTestDialogBuilder(Context context, String hintText, String content) {
            super(context);
            this.mContext = context;
            this.hintText = hintText;
            this.content = content;
        }

        public EditText getEditText() {
            return mEditText;
        }

        @Override
        public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(mContext, 20);
            layout.setPadding(padding, padding, padding, padding);
            mEditText = new EditText(mContext);
            QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
            mEditText.setHint(hintText);
            LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
            editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(mContext, 15);
            mEditText.setLayoutParams(editTextLP);
            layout.addView(mEditText);
            TextView textView = new TextView(mContext);
            textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
            textView.setText(content);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_description));
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(textView);
            return layout;
        }
    }

    // ============================== 展示 popView

    /**
     * 显示 popview 带有一个textview
     *
     * @param context                上下文
     * @param v                      显示在那个控件上方
     * @param content                内容
     * @param width                  宽度
     * @param popViewDismissListener dialog消失时的监听器
     */
    protected void showNormalPopView(Context context, View v, String content, Integer width, PopupWindow.OnDismissListener popViewDismissListener) {
        mNormalPopup = new QMUIPopup(context, QMUIPopup.DIRECTION_NONE);
        TextView textView = new TextView(context);
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                QMUIDisplayHelper.dp2px(context, width == null ? 250 : width),
                WRAP_CONTENT
        ));
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(context, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(context, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(content);
        textView.setTextColor(ContextCompat.getColor(context, R.color.app_color_description));
        mNormalPopup.setContentView(textView);
        if (popViewDismissListener != null) {
            mNormalPopup.setOnDismissListener(popViewDismissListener);
        }
        mNormalPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mNormalPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mNormalPopup.show(v);
    }

    /**
     * 展示一个list类型的PopView
     *
     * @param context                上下文
     * @param v                      展示在哪个view上方
     * @param items                  待展示的item
     * @param width                  popview 的宽度
     * @param height                 popview 的盖度
     * @param itemClickListener      item点击的监听器
     * @param popViewDismissListener Popview消失时的监听器
     */
    protected void showListPopView(Context context, View v, List<String> items, Integer width, Integer height,
                                   AdapterView.OnItemClickListener itemClickListener, PopupWindow.OnDismissListener popViewDismissListener) {
        ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.recyclerview_simple_item, items);
        mListPopup = new QMUIListPopup(context, QMUIPopup.DIRECTION_NONE, adapter);
        mListPopup.create(QMUIDisplayHelper.dp2px(context, width == null ? 250 : width),
                QMUIDisplayHelper.dp2px(context, height == null ? 200 : height), itemClickListener);
        if (popViewDismissListener != null) {
            mListPopup.setOnDismissListener(popViewDismissListener);
        }
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mListPopup.show(v);
    }

    // ============================== bottomSheet

    protected void showSimpleBottomSheetList(Context context, List<String> items, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
        items.forEach(builder::addItem);
        builder.setOnSheetItemClickListener(itemClickListener).build().show();
    }

    protected void showSimpleBottomSheetGrid(Context context, List<Integer> mipmaps, List<String> titles, List<Integer> tags, QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener itemClickListener) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        for (int i = 0; i < mipmaps.size(); i++) {
            if (i < 4) {  // 控制每行的数量为4个
                // 第一行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            } else {
                // 第二行
                builder.addItem(mipmaps.get(i), titles.get(i), tags.get(i), QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
            }
        }
        builder.setOnSheetItemClickListener(itemClickListener).build().show();
    }

    // ============================= solidImgeView
    protected void initSolidImage(ImageView... imageViews) {
        int commonWidth = getResources().getDimensionPixelSize(R.dimen.solid_image_common_width);
        int commonHeight = getResources().getDimensionPixelSize(R.dimen.solid_image_common_height);
        int commonShapeRadius = QMUIDisplayHelper.dp2px(getContext(), 2);
        BitmapDrawable solidImageBitmapDrawable = QMUIDrawableHelper.createDrawableWithSize(getResources(), commonWidth, commonHeight,
                commonShapeRadius, ContextCompat.getColor(getContext(), R.color.app_color_blue));
        for (ImageView imageView : imageViews) {
            imageView.setImageDrawable(solidImageBitmapDrawable);
        }
    }
}
