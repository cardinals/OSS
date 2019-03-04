package com.nobitastudio.oss.controller.collection;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.TestFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

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
