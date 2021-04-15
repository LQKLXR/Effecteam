package com.lqk.effecteam.task.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lqk.effecteam.R;
import com.lqk.effecteam.task.create.TaskCreateActivity;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

/**
 * 任务列表的视图
 */
public class TaskFragment extends Fragment {


    /*两个下拉排序菜单*/
    private MaterialSpinner mCompleteSpinner;
    private MaterialSpinner mTeamTimeSpinner;

    /*循环列表*/
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    /*悬浮按钮*/
    private FloatingActionButton mFloatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mCompleteSpinner = view.findViewById(R.id.task_list_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.task_list_teamtime_spinner);
        mRecyclerView = view.findViewById(R.id.task_list_recyclerview);
        mFloatingActionButton = view.findViewById(R.id.task_list_float_button);
        mCompleteSpinner.setItems("进行中","已完成","全部");
        mTeamTimeSpinner.setItems("优先级","截止时间","创建时间");
        mTaskAdapter = new TaskAdapter(getActivity());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addListener(view);
    }

    private void addListener(View view) {
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TaskCreateActivity.class);
            //TODO 携带项目唯一标识进去
            startActivity(intent);
        });
    }


}