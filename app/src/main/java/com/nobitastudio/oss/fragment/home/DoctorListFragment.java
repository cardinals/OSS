package com.nobitastudio.oss.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nobitastudio.oss.R;
import com.nobitastudio.oss.adapter.recyclerview.DoctorRecycleViewAdapter;
import com.nobitastudio.oss.fragment.standard.StandardWithTobBarLayoutFragment;
import com.nobitastudio.oss.model.entity.Doctor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/29 16:08
 * @description
 */
public class DoctorListFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private void initRecycleView() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        doctors.add(new Doctor());
        DoctorRecycleViewAdapter adapter = new DoctorRecycleViewAdapter(getContext(), doctors);
        adapter.setOnItemClickListener((view, pos) -> startFragment(new DoctorDetailFragment()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
    }

    @Override
    protected String getTopBarTitle() {
        return "医生列表";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_doctor_list;
    }

    @Override
    protected void initLastCustom() {
        initRecycleView();
    }

}
