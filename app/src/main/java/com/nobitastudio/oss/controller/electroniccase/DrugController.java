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
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.model.entity.Drug;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class DrugController extends QMUIWindowInsetLayout {

    @BindView(R.id.drug_detail_solid_imageview)
    ImageView mDrugDetailSolidImageView;
    @BindView(R.id.drug_profile_solid_imageview)
    ImageView mDrugProfileSolidImageView;
    @BindView(R.id.drug_note_solid_imageview)
    ImageView mDrugNoteSolidImageView;

    @BindView(R.id.drug_detail_recyclerview)
    RecyclerView mDrugDetailRecyclerView;

    private static float mShadowAlpha = 1.0f;
    private static int mShadowElevationDp = 10;
    private static int mRadius = 15;

    public static class DrugDetailRecyclerViewAdapter extends BaseRecyclerViewAdapter<Drug> {

        public DrugDetailRecyclerViewAdapter(Context ctx, List<Drug> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.recycleview_item_drug;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, Drug item) {
            QMUILinearLayout mLinearLayout = (QMUILinearLayout) holder.getView(R.id.drug_linearLayout);
            mLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                    QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    protected void init(Context context) {
        new SolidImageHelper(context).initSolidImage(mDrugDetailSolidImageView, mDrugProfileSolidImageView, mDrugNoteSolidImageView);
        mDrugDetailRecyclerView.setAdapter(new DrugDetailRecyclerViewAdapter(context,null));
        mDrugDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public DrugController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_electronic_case_detail_drug, this);
        ButterKnife.bind(this);
        init(context);
    }

}
