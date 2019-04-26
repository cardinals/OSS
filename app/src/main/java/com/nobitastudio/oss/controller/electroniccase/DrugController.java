package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DrugRecyclerViewAdapter;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.QMUILinearLayoutHelper;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.entity.Drug;
import com.nobitastudio.oss.model.vo.DrugAndCount;
import com.nobitastudio.oss.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class DrugController extends BaseController {

    @BindView(R.id.drug_detail_solid_imageview)
    ImageView mDrugDetailSolidImageView;
    @BindView(R.id.drug_profile_solid_imageview)
    ImageView mDrugProfileSolidImageView;
    @BindView(R.id.drug_note_solid_imageview)
    ImageView mDrugNoteSolidImageView;
    @BindView(R.id.use_drug_advise_textview)
    TextView mUseDrugAdviseTextView;
    @BindView(R.id.drug_detail_recyclerview)
    RecyclerView mDrugDetailRecyclerView;
    @BindView(R.id.order_create_time_textview)
    TextView mOrderCreateTimeTextView;
    @BindView(R.id.drug_cost_all_textview)
    TextView mDRugCostAllTextView;

    ElectronicCaseDTO selectedElectronicDTO;
    DrugRecyclerViewAdapter adapter;

    public DrugController(Context context) {
        super(context);
    }

    public DrugController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_electronic_case_detail_drug;
    }

    @Override
    public void initLast() {
        selectedElectronicDTO = mNormalContainerHelper.getSelectedElectronicCase();
        new SolidImageHelper(mContext).initSolidImage(mDrugDetailSolidImageView, mDrugProfileSolidImageView, mDrugNoteSolidImageView);
        adapter = new DrugRecyclerViewAdapter(mContext, generateDrugAndCount());
        adapter.setOnItemClickListener((view,pos) -> showInfoTipDialog("正在开发中"));
        mDrugDetailRecyclerView.setAdapter(adapter);
        mDrugDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mOrderCreateTimeTextView.setText(DateUtil.convertToStandardDateTime(selectedElectronicDTO.getOssOrder().getCreateTime()));
        double allCost = 0;
        for (int i = 0; i < selectedElectronicDTO.getDrugs().size(); i++) {
            allCost += selectedElectronicDTO.getDrugs().get(i).getPrice() * selectedElectronicDTO.getDrugCount().get(i);
        }
        mDRugCostAllTextView.setText(allCost + "元");
        mUseDrugAdviseTextView.setText(selectedElectronicDTO.getElectronicCase().getUseDrugAdvise());
    }

    // 将 drug 以及 对应的数量封装在一个对象中.用于适配器使用
    private List<DrugAndCount> generateDrugAndCount() {
        List<DrugAndCount> drugAndCounts = new ArrayList<>();
        for (int i = 0; i < selectedElectronicDTO.getDrugs().size(); i++) {
            drugAndCounts.add(new DrugAndCount(selectedElectronicDTO.getDrugs().get(i), selectedElectronicDTO.getDrugCount().get(i)));
        }
        return drugAndCounts;
    }
}
