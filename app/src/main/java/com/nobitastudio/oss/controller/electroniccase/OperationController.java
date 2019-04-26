package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.OperationItemRecyclerViewAdapter;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.vo.OperationAndCount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description 电子病历详情中.手术信息
 */
public class OperationController extends BaseController {

    @BindView(R.id.operation_detail_solid_imageview)
    ImageView mOperationDetailSolidImageView;
    @BindView(R.id.operation_profile_solid_imageview)
    ImageView mOperationProfileSolidImageView;
    @BindView(R.id.operation_note_solid_imageview)
    ImageView mOperationNoteSolidImageView;
    @BindView(R.id.operation_detail_recyclerview)
    RecyclerView mOperationDetailRecyclerView;
    @BindView(R.id.operation_cost_all_textview)
    TextView mOperationCostAllTextView;
    @BindView(R.id.operation_advise_textview)
    TextView mOperationAdviseTextView;

    ElectronicCaseDTO selectedElectronicDTO;
    OperationItemRecyclerViewAdapter adapter;

    public OperationController(Context context) {
        super(context);
    }

    public OperationController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
    }

    private List<OperationAndCount> generateOperationItemAndCount() {
        List<OperationAndCount> operationAndCounts = new ArrayList<>();
        for (int i = 0; i < selectedElectronicDTO.getOperationItems().size(); i++) {
            operationAndCounts.add(new OperationAndCount(selectedElectronicDTO.getOperationItems().get(i), selectedElectronicDTO.getOperationItemCount().get(i)));
        }
        return operationAndCounts;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_electronic_case_detail_operation;
    }

    @Override
    public void initLast() {
        selectedElectronicDTO = mNormalContainerHelper.getSelectedElectronicCase();
        new SolidImageHelper(mContext).initSolidImage(mOperationDetailSolidImageView, mOperationProfileSolidImageView, mOperationNoteSolidImageView);
        adapter = new OperationItemRecyclerViewAdapter(mContext, generateOperationItemAndCount());
        adapter.setOnItemClickListener((view,pos) -> showInfoTipDialog("正在开发中"));
        mOperationDetailRecyclerView.setAdapter(adapter);
        mOperationDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        double allCost = 0;
        for (int i = 0; i < selectedElectronicDTO.getOperationItems().size(); i++) {
            allCost += selectedElectronicDTO.getOperationItems().get(i).getPrice() * selectedElectronicDTO.getOperationItemCount().get(i);
        }
        mOperationCostAllTextView.setText(allCost + "元");
        mOperationAdviseTextView.setText(selectedElectronicDTO.getElectronicCase().getOperationAdvise());
    }

}
