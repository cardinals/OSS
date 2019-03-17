package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.DialogHelper;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.Arrays;
import java.util.Collections;
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

    // 将object -> 邮寄实体类   报告邮寄
    public class ExpressRecyclerViewAdapter extends BaseRecyclerViewAdapter<Object> {

        public ExpressRecyclerViewAdapter(Context ctx, List<Object> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_express;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Object item) {
            initQMUILinearLayout(holder.getView(R.id.express_linearLayout));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    private void initRecyclerView() {
        ExpressRecyclerViewAdapter adapter = new ExpressRecyclerViewAdapter(getContext(), null);
        adapter.setOnItemClickListener((v, pos) -> {
            dialog = showNumerousMultiChoiceDialog("请选择需要邮寄的检查报告",
                    "取消", (dialog, index) -> dialog.dismiss(),
                    "提交", (dialog, index, selectedIndexes) -> {
                        dialog.dismiss();
                        Toasty.success(getContext(), Arrays.asList(1, 2, 3).stream().map(item -> {
                            return item + ";";
                        }).collect(Collectors.toList()).toString()).show();
                    }, Arrays.asList("输血", "血细胞分析", "彩超"), new int[]{0, 1}, null);
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

    @Override
    protected String getTopBarTitle() {
        return "门诊检查记录";
    }

    @Override
    protected void initTopBarLast() {
        mTopBar.addRightTextButton("我的申请", R.id.topbar_right_express_text)
                .setOnClickListener(view -> {
                    Toasty.success(getContext(), "进入申请界面").show();
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_express;
    }

    @Override
    protected void initLastCustom() {
        dialog = showLongMessageDialog("注意事项", getString(R.string.express_attention),
                null, null,
                "知道了", (dialog, index) -> {
                    dialog.dismiss();
                });
        dialog.setOnDismissListener(dialog -> {
            Toasty.success(getContext(), "消失了").show();
            // 通过网络，初始化adapter
            initRecyclerView();
        });
    }
}
