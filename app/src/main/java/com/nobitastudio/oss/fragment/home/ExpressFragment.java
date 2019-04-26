package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.ExpressRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.entity.CheckItem;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 邮寄报告
 */
public class ExpressFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mExpressRecyclerView;

    QMUIDialog dialog;
    ExpressRecyclerViewAdapter adapter;
    List<ElectronicCaseDTO> mElectronicCaseDTOS;

    private void initRecyclerView() {
        if (mElectronicCaseDTOS.size() == 0) {
            showInfoTipDialog("该诊疗卡暂无就诊情况", 2000l);
        }
        adapter = new ExpressRecyclerViewAdapter(getContext(), mElectronicCaseDTOS);
        adapter.setOnItemClickListener((v, pos) -> {
            dialog = showNumerousMultiChoiceDialog("请选择需要邮寄的检查报告",
                    "取消", (dialog, index) -> dialog.dismiss(),
                    "提交", (dialog, index, selectedIndexes) -> {
                        dialog.dismiss();
                        showExpressInfo(pos, selectedIndexes);
                    }, generateCheckItem(pos), null, null);
        });
        mExpressRecyclerView.setAdapter(adapter);
        mExpressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    // 展示邮寄报告提示
    private void showExpressInfo(int pos, int[] selectedIndexes) {
        if (selectedIndexes.length == 0) {
            showInfoTipDialog("您未选择任何需要邮寄的检查结果");
        } else {
            StringBuilder builder = new StringBuilder();
            List<CheckItem> checkItems = mElectronicCaseDTOS.get(pos).getCheckItems();
            List<CheckItem> selectedCheckItems = new ArrayList<>();
            for (int i = 0; i < selectedIndexes.length; i++) {
                selectedCheckItems.add(checkItems.get(selectedIndexes[i]));
                builder.append((i + 1) + ". " + checkItems.get(selectedIndexes[i]).getName() + "\n");
            }
            showMessageNegativeDialog("您选择邮寄以下检查报告", builder.toString(),
                    "立刻邮寄", (dialog, index) -> {
                        dialog.dismiss();
                        showNetworkLoadingTipDialog("正在申请邮寄",1500l);
                        getTopBar().postDelayed(() -> {
                            showSuccessTipDialog("申请成功");
                        },1500l);
                    },
                    "重新选择", (dialog, index) -> dialog.dismiss());
        }
    }

    private List<String> generateCheckItem(int pos) {
        List<String> checkItemsChinese = new ArrayList<>();
        for (CheckItem checkItem : mElectronicCaseDTOS.get(pos).getCheckItems()) {
            checkItemsChinese.add(checkItem.getName());
        }
        return checkItemsChinese;
    }

    @Override
    protected String getTopBarTitle() {
        return "检查报告邮寄";
    }

    @Override
    protected void initTopBarLast() {
        mTopBar.addRightTextButton("我的申请", R.id.topbar_right_express_text)
                .setOnClickListener(view -> showInfoTipDialog("正在开发中"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_express;
    }

    @Override
    protected void initLastCustom() {
        mElectronicCaseDTOS = mNormalContainerHelper.getElectronicCases();
        dialog = showLongMessageDialog("注意事项", getString(R.string.express_attention),
                null, null,
                "知道了", (dialog, index) -> {
                    dialog.dismiss();
                });
        dialog.setOnDismissListener(dialog -> {
            initRecyclerView();
        });
    }
}
