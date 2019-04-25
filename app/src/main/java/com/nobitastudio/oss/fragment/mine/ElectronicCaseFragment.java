package com.nobitastudio.oss.fragment.mine;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.EmergencyRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.HospitalizeRecordRecyclerViewAdapter;
import com.nobitastudio.oss.adapter.recyclerview.OutpatientRecordRecyclerViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.dto.ElectronicCaseDTO;
import com.nobitastudio.oss.model.enumeration.ElectronicCaseType;
import com.nobitastudio.oss.model.enumeration.Sex;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description 电子病历
 */
public class ElectronicCaseFragment extends StandardWithTobBarLayoutFragment {

    // 初始化有哪些 pager : 门诊记录  住院记录  急诊记录
    enum Pager {
        OUTPATIENT, HOSPITALIZE, EMERGENCY;

        public static Pager getPagerFromPosition(int position) {
            switch (position) {
                case 0:
                    return OUTPATIENT;
                case 1:
                    return HOSPITALIZE;
                case 2:
                    return EMERGENCY;
                default:
                    return OUTPATIENT;
            }
        }
    }

    @BindView(R.id.medical_card_ownername_textview)
    TextView mMedicalCardOwnerNameTextView;
    @BindView(R.id.medical_card_owner_sex_textview)
    TextView mMedicalCardOwnerSexTextView;
    @BindView(R.id.medical_card_owner_age_textview)
    TextView mMedicalCardOwnerAgeTextView;
    @BindView(R.id.medical_card_id_textview)
    TextView mMedicalCardIdTextView;

    @BindView(R.id.pagers)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    QMUITabSegment mTabSegment;
    private HashMap<Pager, View> mPages;
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        private int mChildCount = 0;

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            View page = mPages.get(Pager.getPagerFromPosition(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };

    OutpatientRecordRecyclerViewAdapter mOutpatientRecordRecyclerViewAdapter;
    HospitalizeRecordRecyclerViewAdapter mHospitalizeRecordRecyclerViewAdapter;
    EmergencyRecyclerViewAdapter mEmergencyRecyclerViewAdapter;

    List<ElectronicCaseDTO> mElectronicCaseDTOS;
    List<ElectronicCaseDTO> mOutpatientElectronicCaseDTOS;
    List<ElectronicCaseDTO> mHospitalizeElectronicCaseDTOS;
    List<ElectronicCaseDTO> mEmergencyElectronicCaseDTOS;

    private void initBasic() {
        showInfoTipDialog("请选择需查看详情的就诊概况",2000l);
        mMedicalCardOwnerNameTextView.setText(mNormalContainerHelper.getSelectedMedicalCard().getOwnerName());
        mMedicalCardOwnerSexTextView.setText(Sex.getChineseSex(mNormalContainerHelper.getSelectedMedicalCard().getOwnerSex()));
        // 511602199705220175  6 ~ 13 是生日（取的时候去 6 ~ 14 因为 subString，最后一个不算）
        String idCard = mNormalContainerHelper.getSelectedMedicalCard().getOwnerIdCard();
        LocalDate bir = LocalDate.of(Integer.valueOf(idCard.substring(6,10)),Integer.valueOf(idCard.substring(10,12)),Integer.valueOf(idCard.substring(12,14)));
        mMedicalCardOwnerAgeTextView.setText(Period.between(bir, LocalDate.now()).getYears() + "岁");
        mMedicalCardIdTextView.setText(mNormalContainerHelper.getSelectedMedicalCard().getId());

        mElectronicCaseDTOS = mNormalContainerHelper.getElectronicCases();
        mOutpatientElectronicCaseDTOS = mElectronicCaseDTOS.stream()
                .filter(item -> item.getElectronicCase().getCaseType().equals(ElectronicCaseType.OUTPATIENT)).collect(Collectors.toList());
        mHospitalizeElectronicCaseDTOS = mElectronicCaseDTOS.stream()
                .filter(item -> item.getElectronicCase().getCaseType().equals(ElectronicCaseType.HOSPITALIZE)).collect(Collectors.toList());
        mEmergencyElectronicCaseDTOS = mElectronicCaseDTOS.stream()
                .filter(item -> item.getElectronicCase().getCaseType().equals(ElectronicCaseType.EMERGENCY)).collect(Collectors.toList());
        mOutpatientRecordRecyclerViewAdapter = new OutpatientRecordRecyclerViewAdapter(getContext(), mOutpatientElectronicCaseDTOS);
        mHospitalizeRecordRecyclerViewAdapter = new HospitalizeRecordRecyclerViewAdapter(getContext(), mHospitalizeElectronicCaseDTOS);
        mEmergencyRecyclerViewAdapter = new EmergencyRecyclerViewAdapter(getContext(), mEmergencyElectronicCaseDTOS);
    }

    private void initPagers() {
        mPages = new HashMap<>();

        RecyclerView mOutpatientRecyclerView = new RecyclerView(getContext());
        RecyclerView mHospitalizeRecyclerView = new RecyclerView(getContext());
        RecyclerView mEmergencyRecyclerView = new RecyclerView(getContext());

        // 进入电子病历详情
        mOutpatientRecordRecyclerViewAdapter.setOnItemClickListener((view, pos) -> {
            mNormalContainerHelper.setSelectedElectronicCase(mOutpatientElectronicCaseDTOS.get(pos));
            startFragment(new ElectronicCaseDetailFragment());
        });
        mHospitalizeRecordRecyclerViewAdapter.setOnItemClickListener((view, pos) -> {
            mNormalContainerHelper.setSelectedElectronicCase(mHospitalizeElectronicCaseDTOS.get(pos));
            startFragment(new ElectronicCaseDetailFragment());
        });
        mEmergencyRecyclerViewAdapter.setOnItemClickListener((view, pos) -> {
            mNormalContainerHelper.setSelectedElectronicCase(mEmergencyElectronicCaseDTOS.get(pos));
            startFragment(new ElectronicCaseDetailFragment());
        });

        mOutpatientRecyclerView.setAdapter(mOutpatientRecordRecyclerViewAdapter);
        mOutpatientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mHospitalizeRecyclerView.setAdapter(mHospitalizeRecordRecyclerViewAdapter);
        mHospitalizeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mEmergencyRecyclerView.setAdapter(mEmergencyRecyclerViewAdapter);
        mEmergencyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        mPages.put(Pager.OUTPATIENT, mOutpatientRecyclerView);
        mPages.put(Pager.HOSPITALIZE, mHospitalizeRecyclerView);
        mPages.put(Pager.EMERGENCY, mEmergencyRecyclerView);

        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                mViewPager.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    private void initTabs() {
        int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        mTabSegment.setDefaultNormalColor(normalColor);
        mTabSegment.setDefaultSelectedColor(selectColor);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorPosition(false);
        mTabSegment.setIndicatorWidthAdjustContent(true);
        QMUITabSegment.Tab mOutpatient = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hospital),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_hospital_selected),
                "门诊记录", false
        );

        QMUITabSegment.Tab mHospitalize = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_stretcher_selected),
                "住院记录", false
        );

        QMUITabSegment.Tab mEmergency = new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_ambulance),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_ambulance_selected),
                "急诊记录", false
        );

        mTabSegment.addTab(mOutpatient)
                .addTab(mHospitalize)
                .addTab(mEmergency);
    }

    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    @Override
    protected Boolean topBarHavDivider() {
        return Boolean.FALSE;
    }

    @Override
    protected String getTopBarTitle() {
        return "电子病历";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_electronic_case;
    }

    @Override
    protected void initLastCustom() {
        initBasic();
        initTabs();
        initPagers();
    }
}
