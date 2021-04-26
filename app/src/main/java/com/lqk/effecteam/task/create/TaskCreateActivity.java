package com.lqk.effecteam.task.create;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.common.calendar.CalenderActivity;
import com.lqk.effecteam.common.data.TaskData;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskCreateActivity extends BaseActivity {

    /*标题栏*/
    private TitleBar mTaskCreateTitleBar;
    /*任务名称*/
    private MultiLineEditText mTaskCreateTaskName;
    /*任务内容*/
    private MultiLineEditText mTaskCreateTaskContent;
    /*任务优先级选择*/
    private SmoothCheckBox mTaskCreateTaskPriorityLow;
    private SmoothCheckBox mTaskCreateTaskPriorityMiddle;
    private SmoothCheckBox mTaskCreateTaskPriorityHigh;
    /*人员选择*/
    private RoundButton mTaskCreateMemberPickButton;
    /*文档选择*/
    private RoundButton mTaskCreateDocPickButton;
    /*最大日期选择*/
    private RoundButton mTaskCreateMaxDateButton;


    private ArrayList<Integer> taskUserList;
    private ArrayList<Integer> taskDocList;
    private String maxDateString;


    private static final int PICK_DATE_REQUEST = 0;
    private static final int PICK_MEMBER_REQUEST = 1;
    private static final int PICK_DOC_REQUEST = 2;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常情况
                    Toast.makeText(TaskCreateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 创建成功的情况
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 3: // 没有权限
                    Toast.makeText(TaskCreateActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        initView();
    }

    private void initView() {
        mTaskCreateTitleBar = findViewById(R.id.task_create_title_bar);
        mTaskCreateTaskName = findViewById(R.id.task_create_task_name);
        mTaskCreateTaskContent = findViewById(R.id.task_create_task_content);
        mTaskCreateTaskPriorityLow = findViewById(R.id.task_create_task_priority_low);
        mTaskCreateTaskPriorityMiddle = findViewById(R.id.task_create_task_priority_middle);
        mTaskCreateTaskPriorityHigh = findViewById(R.id.task_create_task_priority_high);
        mTaskCreateMemberPickButton = findViewById(R.id.task_create_member_pick_button);
        mTaskCreateDocPickButton = findViewById(R.id.task_create_doc_pick_button);
        mTaskCreateMaxDateButton = findViewById(R.id.task_create_max_date_button);
        addListener();
    }

    private void addListener() {

        int projectId = getIntent().getIntExtra("projectId", 0);
        SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        /*标题栏*/
        mTaskCreateTitleBar.setLeftClickListener(v -> {
            finish();
        });
        mTaskCreateTitleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {

                if (mTaskCreateTaskName.isEmpty()
                        || mTaskCreateTaskContent.isEmpty()
                        || (!mTaskCreateTaskPriorityLow.isChecked() && !mTaskCreateTaskPriorityMiddle.isChecked() && !mTaskCreateTaskPriorityHigh.isChecked())
                        || taskUserList == null
                        || taskUserList.size() == 0
                        || maxDateString == null) {
                    Toast.makeText(TaskCreateActivity.this, "缺少必填内容！", Toast.LENGTH_SHORT).show();
                }

                /*开始组装 TaskData 数据*/
                TaskData taskData = new TaskData();
                taskData.setName(mTaskCreateTaskName.getContentText());
                taskData.setContent(mTaskCreateTaskContent.getContentText());
                taskData.setProjectId(projectId);
                taskData.setUserId(userId);
                if (mTaskCreateTaskPriorityLow.isChecked()) {
                    taskData.setPriority(3);
                } else if (mTaskCreateTaskPriorityMiddle.isChecked()) {
                    taskData.setPriority(2);
                } else if (mTaskCreateTaskPriorityHigh.isChecked()) {
                    taskData.setPriority(1);
                }
                taskData.setMaxDateString(maxDateString);
                taskData.setUserIdList(taskUserList);
                taskData.setDocIdList(taskDocList);
                /*结束组装 TaskData 数据*/

                /* 开始网络任务 */
                String url = "createTask";
                Gson gson = new Gson();
                String json = gson.toJson(taskData);
                RequestBody requestBody = RequestBody.create(HttpUtil.JSON_TYPE, json);
                HttpUtil.connectInternet(url, requestBody, new Callback() {
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
                        message.obj = body;
                        if (body.equals("success")){
                            message.what = 2;
                        }
                        else {
                            message.what = 3;
                        }
                        handler.sendMessage(message);

                    }
                });


            }
        });

        /*优先级选择*/
        mTaskCreateTaskPriorityLow.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if (isChecked) {
                mTaskCreateTaskPriorityMiddle.setChecked(false);
                mTaskCreateTaskPriorityHigh.setChecked(false);
            }
        });
        mTaskCreateTaskPriorityMiddle.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if (isChecked) {
                mTaskCreateTaskPriorityLow.setChecked(false);
                mTaskCreateTaskPriorityHigh.setChecked(false);
            }
        });
        mTaskCreateTaskPriorityHigh.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if (isChecked) {
                mTaskCreateTaskPriorityMiddle.setChecked(false);
                mTaskCreateTaskPriorityLow.setChecked(false);
            }
        });

        /*截止日期选择*/
        mTaskCreateMaxDateButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskCreateActivity.this, CalenderActivity.class);
            intent.putExtra("type", PICK_DATE_REQUEST);
            startActivityForResult(intent, PICK_DATE_REQUEST);
        });

        /*人员选择*/
        mTaskCreateMemberPickButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskCreateActivity.this, PickActivity.class);
            intent.putExtra("projectId", projectId);
            intent.putExtra("type", PICK_MEMBER_REQUEST);
            startActivityForResult(intent, PICK_MEMBER_REQUEST);
        });
        /*文档选择*/
        mTaskCreateDocPickButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskCreateActivity.this, PickActivity.class);
            intent.putExtra("projectId", projectId);
            intent.putExtra("type", PICK_DOC_REQUEST);
            startActivityForResult(intent, PICK_DOC_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PICK_MEMBER_REQUEST) {
                ArrayList<Integer> taskUsers = data.getIntegerArrayListExtra("taskUserList");
                this.taskUserList = taskUsers;
                mTaskCreateMemberPickButton.setText("已选择" + taskUserList.size() + "位成员");
            } else if (requestCode == PICK_DOC_REQUEST) {
                ArrayList<Integer> taskDocs = data.getIntegerArrayListExtra("taskDocList");
                this.taskDocList = taskDocs;
                mTaskCreateDocPickButton.setText("已选择" + taskDocList.size() + "个文档");
            } else if (requestCode == PICK_DATE_REQUEST) {
                String dateString = data.getStringExtra("dateString");
                this.maxDateString = dateString;
                mTaskCreateMaxDateButton.setText("已选择 " + dateString);
            }
        }
    }
}