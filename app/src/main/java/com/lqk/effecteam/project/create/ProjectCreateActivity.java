package com.lqk.effecteam.project.create;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.calendar.CalenderActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProjectCreateActivity extends BaseActivity {

    private TitleBar mProjectCreateTitleBar;
    private MultiLineEditText mProjectCreateProjectName;
    private MultiLineEditText mProjectCreateProjectContent;
    private RoundButton mProjectCreateMaxDateButton;

    private String maxDateString;

    private static final int PICK_DATE_REQUEST = 0;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常情况
                    Toast.makeText(ProjectCreateActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 正常情况
                    Intent intent = new Intent();
                    intent.putExtra("toast", "成功创建项目");
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 3: // 其他显示情况

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        initView();
    }

    private void initView() {
        mProjectCreateTitleBar = findViewById(R.id.project_create_title_bar);
        mProjectCreateProjectName = findViewById(R.id.project_create_project_name);
        mProjectCreateProjectContent = findViewById(R.id.project_create_project_content);
        mProjectCreateMaxDateButton = findViewById(R.id.project_create_max_date_button);
        
        addListener();
    }

    private void addListener() {
        mProjectCreateTitleBar.disableLeftView();
        mProjectCreateTitleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {

                SharedPreferences sp = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
                int userId = sp.getInt("userId", 0);
                int teamId = getIntent().getIntExtra("teamId", 0);
                String url = "createProject";
                FormBody formBody = new FormBody.Builder().add("userId", String.valueOf(userId))
                        .add("name", mProjectCreateProjectName.getContentText())
                        .add("maxDate", maxDateString)
                        .add("teamId", String.valueOf(teamId))
                        .add("info", mProjectCreateProjectContent.getContentText())
                        .build();
                HttpUtil.connectInternet(url, formBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = new String(response.body().bytes());
                        if (body.equals("success")){
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });


        /*截止日期选择*/
        mProjectCreateMaxDateButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectCreateActivity.this, CalenderActivity.class);
            intent.putExtra("type", PICK_DATE_REQUEST);
            startActivityForResult(intent, PICK_DATE_REQUEST);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String dateString = data.getStringExtra("dateString");
            mProjectCreateMaxDateButton.setText("已选择 " + dateString);
            this.maxDateString = dateString;
        }
    }
}