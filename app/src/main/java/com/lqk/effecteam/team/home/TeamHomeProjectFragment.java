package com.lqk.effecteam.team.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.project.create.ProjectCreateActivity;
import com.lqk.effecteam.common.entity.Project;
import com.lqk.effecteam.project.list.ProjectAdapter;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/9
 * Describe:
 */
public class TeamHomeProjectFragment extends Fragment {

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
                    //mProjectAdapter.setProjectList(mProjectList);
                    //mProjectAdapter.sort();
                    selectProjectStatus(mCompleteSpinner.getSelectedIndex());
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




    /*悬浮按钮*/
    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_home_project, container, false);
        initView(view);
        loadProjectList();
        return view;
    }


    /**
     * 初始化视图组件
     * @param view
     */
    private void initView(View view){
        mSwipeRefreshLayout = view.findViewById(R.id.team_home_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mCompleteSpinner = view.findViewById(R.id.team_home_project_complete_spinner);
        mTeamTimeSpinner = view.findViewById(R.id.team_home_project_teamtime_spinner);
        mCompleteSpinner.setItems("进行中", "已完成", "全部");
        mTeamTimeSpinner.setItems("队伍名称", "截止时间", "创建时间");
        mRecyclerView = view.findViewById(R.id.team_home_project_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectAdapter = new ProjectAdapter(new ArrayList<Project>(), getActivity(), TeamHomeProjectFragment.this);
        mRecyclerView.setAdapter(mProjectAdapter);


        mFloatingActionButton = view.findViewById(R.id.project_float_button);
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProjectCreateActivity.class);
            int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
            intent.putExtra("teamId", teamId);
            startActivityForResult(intent, 120);
        });

        addListener();
    }

    private void addListener() {
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

        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);

        String urlString = "getTeamProjectList?teamId=" + teamId;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && resultCode == -1) {
            String toast = data.getStringExtra("toast");
            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
            loadProjectList();
        }
    }
}