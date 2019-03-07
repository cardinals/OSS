package com.nobitastudio.oss.controller.collection;

import android.content.Context;
import android.view.LayoutInflater;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.test.TestFragment;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class OtherController extends QMUIWindowInsetLayout {

    @BindView(R.id.emptyView)
    QMUIEmptyView mEmptyView;

    ControllerClickHandler mHandler;

    private OnClickListener getOnclickListener() {
        return v -> {
            QMUICommonListItemView itemView = (QMUICommonListItemView) v;
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("")) {

            }

            mHandler.startFragment(new TestFragment());
        };
    }

    public OtherController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_collect_other, this);
        ButterKnife.bind(this);
    }

}
