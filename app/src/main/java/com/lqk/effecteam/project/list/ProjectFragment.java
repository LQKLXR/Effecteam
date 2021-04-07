package com.lqk.effecteam.project.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lqk.effecteam.R;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

public class ProjectFragment extends Fragment {

    private MaterialSpinner mCompleteSpinner;
    private MaterialSpinner mTeamTimeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        initView(view);
        return view;
    }


    /**
     * 初始化视图组件
     * @param view
     */
    private void initView(View view){
        mCompleteSpinner = view.findViewById(R.id.project_list_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.project_list_teamtime_spinner);
        mCompleteSpinner.setItems("进行中","已完成","全部");
        mTeamTimeSpinner.setItems("队伍名称","截止时间","创建时间");
    }
}