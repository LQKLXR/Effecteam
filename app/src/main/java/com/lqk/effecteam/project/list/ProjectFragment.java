package com.lqk.effecteam.project.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.common.comparator.ProjectComparator;
import com.lqk.effecteam.common.entity.Project;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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


    /*刷新列表*/
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: //网络异常
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();
                    break;
                case 2: //重新加载项目列表
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProjectList = (List<Project>) msg.obj;
                    if (mProjectList.size() == 0){
                        Toast.makeText(getActivity(), "当前没有项目", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_LONG).show();
                        selectProjectStatus(mCompleteSpinner.getSelectedIndex());
                    }

                    break;
                case 3: //输出服务端出来的各种异常
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 4: //归档
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "归档项目成功", Toast.LENGTH_SHORT).show();
                    loadProjectList();
                    break;
                case 5: //删除
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "删除项目成功", Toast.LENGTH_SHORT).show();
                    loadProjectList();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化视图组件
     *
     * @param view
     */
    private void initView(View view) {
        mTitleBar = view.findViewById(R.id.project_list_title_bar);
        mTitleBar.disableLeftView();
        mSwipeRefreshLayout = view.findViewById(R.id.project_list_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mCompleteSpinner = view.findViewById(R.id.project_list_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.project_list_teamtime_spinner);
        mCompleteSpinner.setItems("进行中", "已完成", "全部");
        mTeamTimeSpinner.setItems("队伍名称", "截止时间", "创建时间");
        mRecyclerView = view.findViewById(R.id.project_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectAdapter = new ProjectAdapter(new ArrayList<Project>(), getActivity(), ProjectFragment.this);
        mRecyclerView.setAdapter(mProjectAdapter);

        addListener(view);
        loadProjectList();
    }

    private void addListener(View view) {
        mCompleteSpinner.setOnItemSelectedListener((view12, position, id, item) -> {
            selectProjectStatus(position);
        });
        mTeamTimeSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            switch (position) {
                case 0:
                    mProjectAdapter.setType("TeamName");
                    mProjectAdapter.sort();
                    break;
                case 1:
                    mProjectAdapter.setType("MaxTime");
                    mProjectAdapter.sort();
                    break;
                case 2:
                    mProjectAdapter.setType("StartTime");
                    mProjectAdapter.sort();
                    break;
            }
        });

        // 刷新的时候的监听器
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*刷新的时候重新加载数据*/
                loadProjectList();
            }
        });


    }

    private void selectProjectStatus(int position) {
        if (mProjectList == null || mProjectList.size() == 0){
            return;
        }
        List<Project> selectProjects = new ArrayList<>();
        switch (position) {
            case 0:
                for (Project project : mProjectList) {
                    if (project.getStatus() == 0) {
                        selectProjects.add(project);
                    }
                }
                mProjectAdapter.setProjectList(selectProjects);
                mProjectAdapter.sort();
                break;
            case 1:
                for (Project project : mProjectList) {
                    if (project.getStatus() == 1) {
                        selectProjects.add(project);
                    }
                }
                mProjectAdapter.setProjectList(selectProjects);
                mProjectAdapter.sort();
                break;
            case 2:
                mProjectAdapter.setProjectList(mProjectList);
                mProjectAdapter.sort();
                break;
        }
    }


    /**
     * 加载新的ProjectList
     */
    private void loadProjectList() {
        /*开始转圈加载*/
        mSwipeRefreshLayout.setRefreshing(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        String urlString = "getProjectList?userId=" + userId;
        HttpUtil.connectInternet(urlString, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = new String(response.body().bytes());
                Gson gson = new Gson();
                List<Project> projects = gson.fromJson(body, new TypeToken<List<Project>>() {
                }.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = projects;
                handler.sendMessage(message);
            }
        });
    }


    /**
     * 删除项目
     *
     * @param projectId
     */
    public void deleteProject(int projectId) {
        /*开始转圈加载*/
        mSwipeRefreshLayout.setRefreshing(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        String url = "deleteProject?userId=" + userId + "&projectId=" + projectId;

        HttpUtil.connectInternet(url, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = new String(response.body().bytes());
                Message message = Message.obtain();
                /*成功*/
                if (body.equals("success")) {
                    message.what = 5;
                }
                /*失败*/
                else {
                    message.what = 3;
                    message.obj = body;
                }
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 归档项目
     *
     * @param projectId
     */
    public void completeProject(int projectId) {
        /*开始转圈加载*/
        mSwipeRefreshLayout.setRefreshing(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        String url = "completeProject?userId=" + userId + "&projectId=" + projectId;

        HttpUtil.connectInternet(url, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = new String(response.body().bytes());
                Message message = Message.obtain();
                /*成功*/
                if (body.equals("success")) {
                    message.what = 4;
                }
                /*失败*/
                else {
                    message.what = 3;
                    message.obj = body;
                }
                handler.sendMessage(message);
            }
        });
    }


}