package com.nobitastudio.oss.base.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/02/18 18:02
 * @description
 */
public class DialogHelper {

    Context mContext;

    int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public DialogHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 生成带取消以及确认的对话框，取消及其确认使用的默认的主题颜色(蓝色)，
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public void showMessagePositiveDialog(String title, String content,
                                             String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                             String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 生成带取消以及删除的对话框，删除字体采用的红色字体，
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public void showMessageNegativeDialog(String title, String content,
                                             String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                             String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(0, cancelMsg, QMUIDialogAction.ACTION_PROP_NEGATIVE, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * @param title           题目
     * @param content         很长的内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字  暂时未使用,可以传入null
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器  暂时未使用,客串日null
     */
    public void showLongMessageDialog(String title, String content, String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                      String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancelMsg, cancelListener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 菜单类型对话框
     *
     * @param itemClickListener 选中某一个时的监听器
     * @param items             可供选择的子项名称
     */
    public void showMenuDialog(List<String> items, DialogInterface.OnClickListener itemClickListener) {
        new QMUIDialog.MenuDialogBuilder(mContext)
                .addItems(items.toArray(new String[0]), itemClickListener)
                .create(mCurrentDialogStyle).show();
    }


    /**
     * 带一个单选框的dialog
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     * @param checked         默认是否选中
     */
    public void showConfirmMessageDialog(String title, String content,
                                            String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                            String confirmMsg, QMUIDialogAction.ActionListener confirmListener,
                                            Boolean checked) {
        new QMUIDialog.CheckBoxMessageDialogBuilder(mContext)
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
     * @param listener
     * @param items
     * @param checkedIndex
     */
    public void showSingleChoiceDialog(List<String> items, DialogInterface.OnClickListener listener, int checkedIndex) {
        new QMUIDialog.CheckableDialogBuilder(mContext)
                .setCheckedIndex(checkedIndex)
                .addItems(items.toArray(new String[0]), listener)
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 多选对话框,含有很多单选框,但不是最多
     *
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     */
    public void showMultiChoiceDialog(String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                         String confirmMsg, QMUIDialogAction.ActionListener confirmListener,
                                         List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(mContext)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener);
        builder.addAction(cancelMsg, cancelListener);
        builder.addAction(confirmMsg, confirmListener);
        builder.create(mCurrentDialogStyle).show();
    }

    /**
     * 带有很多的单选框的 dialog
     *
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     */
    public void showNumerousMultiChoiceDialog(String cancelMsg,QMUIDialogAction.ActionListener cancelListener,
                                                 String confirmMsg,QMUIDialogAction.ActionListener confirmListener,
                                                 List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(mContext)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener);
        builder.addAction(cancelMsg, cancelListener);
        builder.addAction(confirmMsg, confirmListener);
        builder.create(mCurrentDialogStyle).show();
    }

    /**
     * 带editTextView 的dialog
     *
     * @param title           标题
     * @param placeHolder     EditTextView 的 hint
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public void showEditTextDialog(String title, String placeHolder,
                                      String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                      String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mContext);
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
     * @param hintText        EditTextView 的 hint
     * @param content         正文
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public void showAutoDialog(String hintText, String content,
                                  String cancelMsg,QMUIDialogAction.ActionListener cancelListener,
                                  String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        QMAutoTestDialogBuilder autoTestDialogBuilder = (QMAutoTestDialogBuilder) new QMAutoTestDialogBuilder(mContext, hintText, content)
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

        public QMAutoTestDialogBuilder(Context mContext) {
            this(mContext, "请输入", "内容正文");
        }

        public QMAutoTestDialogBuilder(Context mContext,String hintText, String content) {
            super(mContext);
            this.mContext = mContext;
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
}
