package com.nobitastudio.oss.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 邮寄报告
 */
public class ExpressFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mExpressRecyclerView;

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

        }
    }

    @Override
    protected String getTopBarTitle() {
        return "邮寄报告";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_express;
    }

    @Override
    protected void initLastCustom() {
        showLongMessageDialog("注意事项", getString(R.string.express_attention),
                null, null,
                "知道了", (dialog, index) -> {
                    // 通过网络，初始化adapter
                    dialog.dismiss();
                });
    }
}
