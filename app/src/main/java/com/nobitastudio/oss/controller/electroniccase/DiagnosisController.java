package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.fragment.test.TestFragment;
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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/28 14:03
 * @description
 */
public class DiagnosisController extends QMUIWindowInsetLayout {

    @BindView(R.id.hosipital_solid_imageview)
    ImageView mHospitalSolidImageView;
    @BindView(R.id.doctor_solid_imageview)
    ImageView mDoctorSolidImageView;
    @BindView(R.id.diagnosis_des_solid_imageview)
    ImageView mDiagnosisDesSolidImageView;

    SolidImageHelper mSolidImageHelper;

    protected void init() {
        mSolidImageHelper = new SolidImageHelper(getContext());
        mSolidImageHelper.initSolidImage(mHospitalSolidImageView, mDoctorSolidImageView, mDiagnosisDesSolidImageView);
    }

    public DiagnosisController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.controller_electronic_case_detail_diagnosis, this);
        ButterKnife.bind(this);
        init();
    }

}
