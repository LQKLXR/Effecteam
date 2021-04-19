package com.lqk.effecteam.team.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lqk.effecteam.R;
import com.lqk.effecteam.project.create.ProjectCreateActivity;
import com.lqk.effecteam.project.ProjectVirtualData;
import com.lqk.effecteam.project.list.Project;
import com.lqk.effecteam.project.list.ProjectAdapter;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/9
 * Describe:
 */
public class TeamHomeProjectFragment extends Fragment {

    private MaterialSpinner mCompleteSpinner;
    private MaterialSpinner mTeamTimeSpinner;

    private RecyclerView mRecyclerView;
    private ProjectAdapter mProjectAdapter;
    private List<Project> mProjectList;

    /*悬浮按钮*/
    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_home_project, container, false);
        initView(view);
        return view;
    }


    /**
     * 初始化视图组件
     * @param view
     */
    private void initView(View view){
        mCompleteSpinner = view.findViewById(R.id.team_home_project_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.team_home_project_teamtime_spinner);
        mCompleteSpinner.setItems("进行中","已完成","全部");
        mTeamTimeSpinner.setItems("队伍名称","截止时间","创建时间");
        mRecyclerView = view.findViewById(R.id.team_home_project_recyclerview);
        /* TODO 虚拟数据 */
        mProjectList = new ArrayList<>();
        for (Project p : ProjectVirtualData.projectList){
            if(p.getOwnerTeamId() == 1){
                mProjectList.add(p);
            }
        }
        mProjectAdapter = new ProjectAdapter(mProjectList, getActivity());
        mRecyclerView.setAdapter(mProjectAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = view.findViewById(R.id.project_float_button);
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProjectCreateActivity.class);
            //TODO 携带项目唯一标识进去
            startActivity(intent);
        });
    }
}
