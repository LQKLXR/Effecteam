package com.lqk.effecteam.project.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.project.ProjectVirtualData;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.util.List;

public class ProjectFragment extends Fragment {

    /*标题栏*/
    private TitleBar mTitleBar;

    /*两个下拉排序菜单*/
    private MaterialSpinner mCompleteSpinner;
    private MaterialSpinner mTeamTimeSpinner;

    /*循环列表相关*/
    private RecyclerView mRecyclerView;
    private ProjectAdapter mProjectAdapter;
    private List<Project> mProjectList;


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
        mTitleBar = view.findViewById(R.id.project_list_title_bar);
        mTitleBar.disableLeftView();
        mCompleteSpinner = view.findViewById(R.id.project_list_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.project_list_teamtime_spinner);
        mCompleteSpinner.setItems("进行中","已完成","全部");
        mTeamTimeSpinner.setItems("队伍名称","截止时间","创建时间");
        mRecyclerView = view.findViewById(R.id.project_list_recyclerview);
        /* TODO 虚拟数据 */
        mProjectList = ProjectVirtualData.projectList;
        mProjectAdapter = new ProjectAdapter(mProjectList, getActivity());
        mRecyclerView.setAdapter(mProjectAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}