package com.nobitastudio.oss.fragment.about;

import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DeclarationFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.copyright_textView)
    TextView mCopyrightTextView;

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("声明");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_declaration;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);

    }
}
