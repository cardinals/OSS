package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class LicenseAgreementFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("许可协议");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_license_agreement;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
    }

}
