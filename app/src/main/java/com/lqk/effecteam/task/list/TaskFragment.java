package com.lqk.effecteam.task.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.lqk.effecteam.common.util.HttpUtil;
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

    public static final int TASK_COMPLETE = 0;
    public static final int TASK_DELETE = 1;

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
    int type;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常的情况
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 正常的情况
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<Task> tasks = (List<Task>) msg.obj;
                    if (tasks.size() == 0) {
                        //Toast.makeText(getActivity(), "当前没有任务", Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                        taskList = tasks;
                        selectTaskByStatus(mCompleteSpinner.getSelectedIndex());
                    }
                    break;
                case 3: // 其它的显示情况
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
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
        mTaskAdapter = new TaskAdapter(getActivity(), TaskFragment.this, new ArrayList<>());
        mRecyclerView.setAdapter(mTaskAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = view.findViewById(R.id.task_list_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        addListener(view);

        loadTaskList();
    }

    private void addListener(View view) {

        /*点击悬浮按钮进入创建任务的界面*/
        mFloatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TaskCreateActivity.class);
            int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
            intent.putExtra("projectId", projectId);
            startActivityForResult(intent, 10);
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

        mCompleteSpinner.setOnItemSelectedListener((view12, position, id, item) -> selectTaskByStatus(position));

    }

    /**
     * 根据当前任务Fragment所在的位置调用合适的加载任务的方法
     */
    public void loadTaskList() {
        if (type == 0) {
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

    /**
     * 加载项目的任务列表
     */
    private void loadProjectTaskList() {
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
                List<Task> tasks = gson.fromJson(body, new TypeToken<List<Task>>() {
                }.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = tasks;
                handler.sendMessage(message);
            }
        });

    }

    /**
     * 加载我的任务列表
     */
    private void loadMyTaskList() {
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
                List<Task> tasks = gson.fromJson(body, new TypeToken<List<Task>>() {
                }.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = tasks;
                handler.sendMessage(message);
            }
        });
    }


    /**
     * 完成任务
     *
     * @param taskId
     */
    public void operateTask(int taskId, int operateId) {
        int userId = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getInt("userId", 0);
        String url = null;
        String successMessage = null;
        if (operateId == TASK_COMPLETE) {
            url = "completeTask?taskId=" + taskId;
            successMessage = "完成任务成功";
        } else if (operateId == TASK_DELETE) {
            url = "deleteTask?taskId=" + taskId;
            successMessage = "删除任务成功";
        }
        url = url + "&userId=" + userId;

        String finalSuccessMessage = successMessage;
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
                if (body.equals("success")) {
                    message.what = 3;
                    message.obj = finalSuccessMessage;
                } else {
                    message.what = 3;
                    message.obj = body;
                }
                handler.sendMessage(message);

            }
        });
    }

    /**
     * 根据任务的状态挑选出对应的任务
     *
     * @param position
     */
    private void selectTaskByStatus(int position) {
        if (taskList == null) {
            return;
        }
        List<Task> tasks = new ArrayList<>();
        switch (position) {
            case 0:
                for (Task task : taskList) {
                    if (task.getStatus() == 0) {
                        tasks.add(task);
                    }
                }
                mTaskAdapter.setTaskList(tasks);
                mTaskAdapter.sort();
                break;
            case 1:
                for (Task task : taskList) {
                    if (task.getStatus() == 1) {
                        tasks.add(task);
                    }
                }
                mTaskAdapter.setTaskList(tasks);
                mTaskAdapter.sort();
                break;
            case 2:
                mTaskAdapter.setTaskList(taskList);
                mTaskAdapter.sort();
                break;
        }
    }


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1) {
            Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_SHORT).show();
            loadTaskList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTaskList();
    }
}