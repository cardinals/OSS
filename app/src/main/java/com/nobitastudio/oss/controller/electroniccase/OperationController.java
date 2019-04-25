package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.adapter.BaseRecyclerViewAdapter;
import com.nobitastudio.oss.base.adapter.RecyclerViewHolder;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.model.entity.Drug;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description 电子病历详情中.手术信息
 */
public class OperationController extends QMUIWindowInsetLayout {

    @BindView(R.id.check_detail_solid_imageview)
    ImageView mCheckDetailSolidImageView;
    @BindView(R.id.check_profile_solid_imageview)
    ImageView mCheckProfileSolidImageView;
    @BindView(R.id.check_note_solid_imageview)
    ImageView mCheckNoteSolidImageView;

    @BindView(R.id.drug_detail_recyclerview)
    RecyclerView mDrugDetailRecyclerView;

    private QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public class DrugDetailRecyclerViewAdapter extends BaseRecyclerViewAdapter<Drug> {

        public DrugDetailRecyclerViewAdapter(Context ctx, List<Drug> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_check;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Drug item) {
            mQMUILinearLayoutHelper.init(holder.getView(R.id.check_linearLayout));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    protected void init(Context context) {
        new SolidImageHelper(context).initSolidImage(mCheckDetailSolidImageView, mCheckProfileSolidImageView, mCheckNoteSolidImageView);
        mDrugDetailRecyclerView.setAdapter(new DrugDetailRecyclerViewAdapter(context,null));
        mDrugDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(context);
    }

    public OperationController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_electronic_case_detail_check, this);
        ButterKnife.bind(this);
        init(context);
    }

}
