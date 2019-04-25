package com.nobitastudio.oss.controller.electroniccase;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nobitastudio.oss.R;
import com.nobitastudio.oss.base.controller.BaseController;
import com.nobitastudio.oss.base.helper.SolidImageHelper;
import com.nobitastudio.oss.base.inter.ControllerClickHandler;
import com.nobitastudio.oss.container.ConstantContainer;
import com.nobitastudio.oss.fragment.test.TestFragment;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.enumeration.DoctorLevel;
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
public class DiagnosisController extends BaseController {

    @BindView(R.id.hosipital_solid_imageview)
    ImageView mHospitalSolidImageView;
    @BindView(R.id.doctor_solid_imageview)
    ImageView mDoctorSolidImageView;
    @BindView(R.id.diagnosis_des_solid_imageview)
    ImageView mDiagnosisDesSolidImageView;
    @BindView(R.id.diagnosis_advise_solid_imageview)
    ImageView mDiagnosisAdviseSolidImageView;
    @BindView(R.id.doctor_circleimageview)
    CircleImageView mDoctorHeadImg;
    @BindView(R.id.doctor_name_textview)
    TextView mDoctorNameTextView;
    @BindView(R.id.doctor_level_textview)
    TextView mDoctorLevelTextView;
    @BindView(R.id.department_textview)
    TextView mDepartmentTextView;
    @BindView(R.id.submajor_textview)
    TextView mSubMajorTextView;
    @BindView(R.id.speciality_textview)
    TextView mSpecialityTextView;
    @BindView(R.id.diagnosis_des_textview)
    TextView mDiagnosisDesTextView;
    @BindView(R.id.diagnosis_advise_textview)
    TextView DiagnosisAdviseTextView;

    SolidImageHelper mSolidImageHelper;
    ElectronicCaseDTO selectedElectronicDTO;

    @Override
    public void refresh(Boolean isCancelPull) {
        selectedElectronicDTO = mNormalContainerHelper.getSelectedElectronicCase();
        Glide.with(mContext).load(ConstantContainer.OSS_SERVER_RUNTIME + selectedElectronicDTO
                .getRegistrationAll().getDoctor().getIconUrl()).into(mDoctorHeadImg);
        mDoctorNameTextView.setText(selectedElectronicDTO.getRegistrationAll().getDoctor().getName());
        mDoctorLevelTextView.setText(DoctorLevel.translateToString(selectedElectronicDTO.getRegistrationAll().getDoctor().getLevel()));
        mDepartmentTextView.setText(selectedElectronicDTO.getRegistrationAll().getDepartment().getName());
        mSubMajorTextView.setText(selectedElectronicDTO.getRegistrationAll().getDoctor().getSubMajor());
        mSpecialityTextView.setText(selectedElectronicDTO.getRegistrationAll().getDoctor().getSpecialty());
        mDiagnosisDesTextView.setText(selectedElectronicDTO.getElectronicCase().getDiagnosisDes());
        DiagnosisAdviseTextView.setText(selectedElectronicDTO.getElectronicCase().getDiagnosisAdvise());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.controller_electronic_case_detail_diagnosis;
    }

    @Override
    public void initLast() {
        mSolidImageHelper = new SolidImageHelper(getContext());
        mSolidImageHelper.initSolidImage(mHospitalSolidImageView, mDoctorSolidImageView, mDiagnosisDesSolidImageView, mDiagnosisAdviseSolidImageView);
        refresh(false);
    }

    public DiagnosisController(Context context) {
        super(context);
    }

    public DiagnosisController(Context context, ControllerClickHandler mHandler) {
        super(context, mHandler);
    }
}
