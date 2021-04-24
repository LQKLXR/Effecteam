package com.lqk.effecteam.task.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.common.entity.Task;
import com.lqk.effecteam.task.create.TaskCreateActivity;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    private List<Task> taskList;

    /*下拉刷新*/
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /* Type 表示要显示的任务类型。 0:项目里的任务; 1:个人界面的任务*/
    int type ;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<Task> tasks = (List<Task>) msg.obj;
                    if (tasks.size() == 0){
                        Toast.makeText(getActivity(), "任务列表为空", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                        taskList = tasks;
                        mTaskAdapter.setTaskList(taskList);
                        mTaskAdapter.sort();
                        break;
                    }

            }
        }
    };

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
        mCompleteSpinner.setItems("进行中", "已完成", "全部");
        mTeamTimeSpinner.setItems("优先级", "截止时间", "创建时间");
        mTaskAdapter = new TaskAdapter(getActivity(), new ArrayList<>());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = view.findViewById(R.id.task_list_refresh);

        addListener(view);
        /*项目任务*/
        if (type == 0){
            loadProjectTaskList();
            mSwipeRefreshLayout.setOnRefreshListener(this::loadProjectTaskList);
        }
        /*我的任务*/
        else {
            loadMyTaskList();
            mSwipeRefreshLayout.setOnRefreshListener(this::loadMyTaskList);
            mFloatingActionButton.setVisibility(View.GONE);
        }
    }

    private void addListener(View view) {

        /*点击悬浮按钮进入创建任务的界面*/
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TaskCreateActivity.class);
            int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
            intent.putExtra("projectId", projectId);
            startActivity(intent);
        });

        mTeamTimeSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            switch (position) {
                case 0:
                    mTaskAdapter.sortType("Priority");
                    mTaskAdapter.sort();
                    break;
                case 1:
                    mTaskAdapter.sortType("MaxTime");
                    mTaskAdapter.sort();
                    break;
                case 2:
                    mTaskAdapter.sortType("StartTime");
                    mTaskAdapter.sort();
                    break;
            }
        });

    }


    public void loadProjectTaskList(){
        mSwipeRefreshLayout.setRefreshing(true);
        int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
        String url = "getProjectTaskList?projectId=" + projectId;
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
                Gson gson = new Gson();
                /*解析任务列表的json*/
                List<Task> tasks = gson.fromJson(body, new TypeToken<List<Task>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = tasks;
                handler.sendMessage(message);
            }
        });

    }

    public void loadMyTaskList(){
        mSwipeRefreshLayout.setRefreshing(true);
        int userId = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getInt("userId", 0);
        String url = "getMyTaskList?userId=" + userId;
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
                Gson gson = new Gson();
                /*解析任务列表的json*/
                List<Task> tasks = gson.fromJson(body, new TypeToken<List<Task>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = tasks;
                handler.sendMessage(message);
            }
        });
    }


    public void setType(int type) {
        this.type = type;
    }
}