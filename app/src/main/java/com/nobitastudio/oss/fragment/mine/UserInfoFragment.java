package com.nobitastudio.oss.fragment.mine;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.login.VerificationCodeFragment;
import com.nobitastudio.oss.fragment.old.ForgetPasswordTwoFragment;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.enumeration.Sex;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class UserInfoFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    QMUICommonListItemView mUsernameItemView;
    QMUICommonListItemView mSexItemView;
    QMUICommonListItemView mModifyPasswordItemView;
    QMUICommonListItemView mIdCardItemView;
    QMUICommonListItemView mMobileItemView;
    QMUICommonListItemView mWechatItemView;
    QMUICommonListItemView mQQItemView;
    QMUICommonListItemView mWeiboItemView;
    QMUICommonListItemView mAliPayItemView;

    @OnClick({R.id.user_img_linearLayout, R.id.save_info_button})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_img_linearLayout:
                showSimpleBottomSheetList(Arrays.asList("拍照", "相簿相片"), (dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    showInfoTipDialog("正在开发中");
                });
                break;
            case R.id.save_info_button:
                showInfoTipDialog("正在开发中");
                break;
        }
    }

    private void initGroupListView() {
        // 基本信息
        mUsernameItemView = mGroupListView.createItemView(
                null,
                "用户名",
                mNormalContainerHelper.getUser().getName(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mSexItemView = mGroupListView.createItemView(
                null,
                "性别",
                "保密",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mModifyPasswordItemView = mGroupListView.createItemView(
                null,
                "修改密码",
                "仅支持通过手机验证修改",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mIdCardItemView = mGroupListView.createItemView(
                null,
                "身份证号",
                mNormalContainerHelper.getUser().getIdCard() == null ? "未设置" : mNormalContainerHelper.getUser().getIdCard(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("基本信息")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(mUsernameItemView, getOnclickListener())
                .addItemView(mSexItemView, getOnclickListener())
                .addItemView(mModifyPasswordItemView, getOnclickListener())
                .addItemView(mIdCardItemView, getOnclickListener())
                .addTo(mGroupListView);

        //  绑定信息
        mMobileItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_china),
                "手机",
                "+86 " + mNormalContainerHelper.getUser().getMobile(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mWechatItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.wechat),
                "微信",
                "未绑定",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mQQItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.qq),
                "QQ",
                "未绑定",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mWeiboItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.weibo),
                "微博",
                "未绑定",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mAliPayItemView = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_ali_pay),
                "支付宝",
                "未绑定",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("绑定信息:绑定后可进行登录")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(mMobileItemView, getOnclickListener())
                .addItemView(mWechatItemView, getOnclickListener())
                .addItemView(mQQItemView, getOnclickListener())
                .addItemView(mWeiboItemView, getOnclickListener())
                .addItemView(mAliPayItemView, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("用户名")) {
                showEditTextDialog("修改用户名", "请输入新用户名",
                        "取消", (dialog, index) -> dialog.dismiss(),
                        "确认修改", (dialog, index) -> dialog.dismiss());
            } else if (itemViewText.equals("性别")) {
                List<String> sexes = Arrays.asList("男", "女", "保密");
                showListPopView(mSexItemView.getDetailTextView(), sexes, 120, 160, (parent, view, position, id) -> {
                    mSexItemView.setDetailText(sexes.get(position));
                    popViewDismiss();
                }, null);
            } else if (itemViewText.equals("修改密码")) {
                mNormalContainerHelper.setInputMobile(mNormalContainerHelper.getUser().getMobile());
                mNormalContainerHelper.setInputMobileFragment(NormalContainer.InputMobileFor.MODIFY_PASSWORD);
                startFragment(new VerificationCodeFragment());
            } else if (itemViewText.equals("身份证号")) {
                showInfoTipDialog("正在开发中");
            } else if (itemViewText.equals("手机")) {
                showInfoTipDialog("正在开发中");
            } else if (itemViewText.equals("微信")) {
                showInfoTipDialog("正在开发中");
            } else if (itemViewText.equals("QQ")) {
                showInfoTipDialog("正在开发中");
            } else if (itemViewText.equals("微博")) {
                showInfoTipDialog("正在开发中");
            }
        };
    }

    @Override
    protected String getTopBarTitle() {
        return "个人资料";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initLastCustom() {
        initGroupListView();
    }
}
