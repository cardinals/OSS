package com.nobitastudio.oss.fragment.home;

import android.view.View;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 用户输入新密码 以及 确认密码
 */
public class CreateMedicalCardThreeFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    QMUICommonListItemView mMedicalCardOwnerNameItemView;
    QMUICommonListItemView mSexItemView;
    QMUICommonListItemView mIdCardItemView;
    QMUICommonListItemView mMedicalCardOwnerAddressItemView;

    @OnClick({R.id.confirm_modify_password})
    void onClick(View v) {
        // 清除所有fragment 进入homeFragment
        // clearAllFragment();
        startFragmentAndDestroyCurrent(new HomeFragment());
    }

    private void initGroupListView() {
        mMedicalCardOwnerNameItemView = mGroupListView.createItemView(
                null,
                "姓名",
                "请输入",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mSexItemView = mGroupListView.createItemView(
                null,
                "性别",
                "请选择",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mIdCardItemView = mGroupListView.createItemView(
                null,
                "身份证号",
                "请输入",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        mMedicalCardOwnerAddressItemView = mGroupListView.createItemView(
                null,
                "联系地址",
                "请输入",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(mMedicalCardOwnerNameItemView, getOnclickListener())
                .addItemView(mSexItemView, getOnclickListener())
                .addItemView(mIdCardItemView, getOnclickListener())
                .addItemView(mMedicalCardOwnerAddressItemView, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            QMUICommonListItemView itemView = (QMUICommonListItemView) v;
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("姓名")) {
                showAutoDialog("请输入诊疗卡持有者姓名", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
            } else if (itemViewText.equals("性别")) {
                showListPopView(mSexItemView.getDetailTextView(), Arrays.asList("男", "女", "保密"), 120, 160, (parent, view, position, id) -> {
                    popViewDismiss();
                }, null);
            } else if (itemViewText.equals("身份证号")) {
                showAutoDialog("请输入诊疗卡持有者身份证号", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
            } else if (itemViewText.equals("联系地址")) {
                showAutoDialog("请输入诊疗卡持有者联系地址", getString(R.string.create_medical_card_attention),
                        "取消", (dialog, index) -> {
                            dialog.dismiss();
                        },
                        "确定", (dialog, index) -> {
                            dialog.dismiss();
                        });
            }
        };
    }

    @Override
    protected String getTopBarTitle() {
        return "确认办理";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_passwor_three;
    }

    @Override
    protected void initLastCustom() {
        initGroupListView();
    }
}
