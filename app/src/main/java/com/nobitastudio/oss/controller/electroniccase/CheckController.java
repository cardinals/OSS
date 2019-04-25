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
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
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
public class CheckController extends BaseController {

    @BindView(R.id.check_detail_solid_imageview)
    ImageView mCheckDetailSolidImageView;
    @BindView(R.id.check_profile_solid_imageview)
    ImageView mCheckProfileSolidImageView;
    @BindView(R.id.check_note_solid_imageview)
    ImageView mCheckNoteSolidImageView;

    @BindView(R.id.check_detail_recyclerview)
    RecyclerView mCheckDetailRecyclerView;

    private QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    public CheckController(Context context) {
        super(context);
    }

    public CheckController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
    }

    protected void init(Context context) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_electronic_case_detail_check;
    }

    @Override
    public void initLast() {
//        new SolidImageHelper(mContext).initSolidImage(mCheckDetailSolidImageView, mCheckProfileSolidImageView, mCheckNoteSolidImageView);
//        mDrugDetailRecyclerView.setAdapter(new DrugDetailRecyclerViewAdapter(mContext,null));
//        mDrugDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//            }
//        });
//        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
    }

}
