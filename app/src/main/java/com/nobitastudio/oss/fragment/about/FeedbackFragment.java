package com.nobitastudio.oss.fragment.about;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class FeedbackFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.feedback_editText)
    EditText mFeedbackEditText;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    @OnClick({R.id.feedback_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_button:
                showNetworkLoadingTipDialog("正在发送您的反馈",1500);
                break;
            default:
                break;
        }
    }

    private void initQMUILinearLayout() {
        float mShadowAlpha = 1.0f;
        int mShadowElevationDp = 0;
        int mRadius = 6;
        initQMUILinearLayout(mQMUILinearLayout,mShadowAlpha,mShadowElevationDp,mRadius);
    }

    @Override
    protected String getTopBarTitle() {
        return "反馈";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
        initQMUILinearLayout();
    }

}
