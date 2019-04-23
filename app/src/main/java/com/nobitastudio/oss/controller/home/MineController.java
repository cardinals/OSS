package com.nobitastudio.oss.controller.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.DialogHelper;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.container.NormalContainer;
import com.nobitastudio.oss.fragment.mine.ElectronicCaseFragment;
import com.nobitastudio.oss.fragment.home.MedicalCardFragment;
import com.nobitastudio.oss.fragment.mine.MyCollectFragment;
import com.nobitastudio.oss.fragment.mine.OrderFragment;
import com.nobitastudio.oss.fragment.mine.RegisterRecordFragment;
import com.nobitastudio.oss.fragment.mine.SettingFragment;
import com.nobitastudio.oss.fragment.mine.UserInfoFragment;
import com.nobitastudio.oss.fragment.mine.WaitDiagnosisFragment;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class MineController extends BaseController {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;
    @BindView(R.id.head_imageview)
    CircleImageView mUserHeadImageView;
    @BindView(R.id.username_textview)
    TextView mUsernameTextView;

    DialogHelper mDialogHelper;
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    @OnClick({R.id.head_imageview, R.id.username_textview,
            R.id.wait_diagnosis_linearlayout, R.id.order_linearlayout, R.id.my_collection_linearlayout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_imageview:
            case R.id.username_textview:
                mHandler.startFragment(new UserInfoFragment());
                break;
            case R.id.wait_diagnosis_linearlayout:
                mNormalContainerHelper.setDiagnosisTypePos(0);  // 进入时默认选中待就诊
                mHandler.startFragment(new WaitDiagnosisFragment());
                break;
            case R.id.order_linearlayout:
                mHandler.startFragment(new OrderFragment());
                break;
            case R.id.my_collection_linearlayout:
                mHandler.startFragment(new MyCollectFragment());
                break;
        }
    }

    private void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle(getResources().getString(R.string.mine));
        mTopBar.addRightImageButton(R.mipmap.ic_setting, R.id.topbar_right_setting_button)
                .setOnClickListener(view -> mHandler.startFragment(new SettingFragment()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_mine;
    }

    @Override
    public void initLast() {
        initTopBar();
        initQMUILinearLayout();
        initRefreshLayout();
        initGroupListView();
        initLastCustom();
    }

    private void initQMUILinearLayout() {
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
        mQMUILinearLayoutHelper.init(mQMUILinearLayout);
    }

    private void initGroupListView() {

        // 常用工具
        QMUICommonListItemView registerRecordItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_history),
                "挂号记录",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicCaseHistory = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_electronic_case),
                "电子病历",
                "验证密码后方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicPrescriptionItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_tcm),
                "电子处方",
                "验证密码后方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView finishDiagnosisItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_finish_diagnosis),
                "已就诊",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(registerRecordItem, getOnclickListener())
                .addItemView(electronicCaseHistory, getOnclickListener())
                .addItemView(electronicPrescriptionItem, getOnclickListener())
                .addItemView(finishDiagnosisItem, getOnclickListener())
                .addTo(mGroupListView);

        //  普通工具
        QMUICommonListItemView myConsultingItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_consulting),
                "我的咨询",
                "可查看咨询的历史对话信息",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView myMedicalCardsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_medical_card),
                "我的诊疗卡",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("普通工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(myConsultingItem, getOnclickListener())
                .addItemView(myMedicalCardsItem, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("挂号记录")) {
                mHandler.startFragment(new RegisterRecordFragment());
            } else if (itemViewText.equals("电子病历")) {
                showInfoTipDialog("请选择需要查看的诊疗卡");
                mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.ELECTRONIC_CASE);
                mHandler.startFragment(new MedicalCardFragment());
//                mDialogHelper.showAutoDialog("请输入诊疗卡密码(非登录密码)", mContext.getString(R.string.warm_prompt_electronic_case),
//                        "取消", (dialog, index) -> dialog.dismiss(),
//                        "确定", (dialog, index,content) -> {
//                            dialog.dismiss();
//                            mHandler.startFragment(new ElectronicCaseFragment());
//                        });
            } else if (itemViewText.equals("电子处方")) {
                showInfoTipDialog("请选择需要查看的诊疗卡");
                mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.DRUG_DETAIL);
                mHandler.startFragment(new MedicalCardFragment());
            } else if (itemViewText.equals("已就诊")) {
                mNormalContainerHelper.setDiagnosisTypePos(1);  // 进入时默认选中 已就诊
                mHandler.startFragment(new WaitDiagnosisFragment());
            } else if (itemViewText.equals("我的咨询")) {
            } else if (itemViewText.equals("我的诊疗卡")) {
                mNormalContainerHelper.setEnterMedicalCardFor(NormalContainer.EnterMedicalCardFor.NORMAL);
                mHandler.startFragment(new MedicalCardFragment());
            }
        };
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }

    protected void initLastCustom() {
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
        mUsernameTextView.setText(mNormalContainerHelper.getUser().getName());
        // 初始化头像
        if (mNormalContainerHelper.getUser().getHeadImg() != null && !mNormalContainerHelper.getUser().getHeadImg().isEmpty()) {

        }
    }

    public MineController(Context mContext, ControllerClickHandler mHandler) {
        super(mContext, mHandler);
    }
}
