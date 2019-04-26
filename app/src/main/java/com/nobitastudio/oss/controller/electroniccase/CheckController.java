package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.CheckItemRecyclerViewAdapter;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.vo.CheckItemAndCount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.check_cost_all_textview)
    TextView mCheckCostAllTextView;
    @BindView(R.id.check_des_textview)
    TextView mCheckDesTextView;

    ElectronicCaseDTO selectedElectronicDTO;
    CheckItemRecyclerViewAdapter adapter;

    public CheckController(Context context) {
        super(context);
    }

    public CheckController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
    }

    private List<CheckItemAndCount> generateCheckItemAndCount() {
        List<CheckItemAndCount> checkItemAndCounts = new ArrayList<>();
        for (int i = 0; i < selectedElectronicDTO.getCheckItems().size(); i++) {
            checkItemAndCounts.add(new CheckItemAndCount(selectedElectronicDTO.getCheckItems().get(i), selectedElectronicDTO.getCheckItemCount().get(i)));
        }
        return checkItemAndCounts;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_electronic_case_detail_check;
    }

    @Override
    public void initLast() {
        selectedElectronicDTO = mNormalContainerHelper.getSelectedElectronicCase();
        new SolidImageHelper(mContext).initSolidImage(mCheckDetailSolidImageView, mCheckProfileSolidImageView, mCheckNoteSolidImageView);
        adapter = new CheckItemRecyclerViewAdapter(mContext, generateCheckItemAndCount());
        adapter.setOnItemClickListener((view,pos) -> showInfoTipDialog("正在开发中"));
        mCheckDetailRecyclerView.setAdapter(adapter);
        mCheckDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        double allCost = 0;
        for (int i = 0; i < selectedElectronicDTO.getCheckItems().size(); i++) {
            allCost += selectedElectronicDTO.getCheckItems().get(i).getPrice() * selectedElectronicDTO.getCheckItemCount().get(i);
        }
        mCheckCostAllTextView.setText(allCost + "元");
        mCheckDesTextView.setText(selectedElectronicDTO.getElectronicCase().getCheckDes());
    }

}
