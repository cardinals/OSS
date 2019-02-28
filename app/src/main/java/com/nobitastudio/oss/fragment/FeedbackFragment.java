package com.nobitastudio.oss.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.nobitastudio.oss.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @OnClick({R.id.feedback_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_button:
                showNetworkLoadingTipDialog("正在发送您的反馈");
                mTopBar.postDelayed(() -> showSuccessTipDialog("感谢您的反馈"),1500);
                break;
            default:
                break;
        }
    }

    private void initQMUILinearLayout() {
        float mShadowAlpha = 1.0f;
        int mShadowElevationDp = 0;
        int mRadius = 6;
        mQMUILinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(getContext(), mRadius),
                QMUIDisplayHelper.dp2px(getContext(), mShadowElevationDp), mShadowAlpha);
    }

    @Override
    protected void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(view -> this.popBackStack());
        mTopBar.setTitle("反馈");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initLastCustom() {
        initQMUILinearLayout();
    }

}
