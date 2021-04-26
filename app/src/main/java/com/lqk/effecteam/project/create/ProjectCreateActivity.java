package com.lqk.effecteam.project.create;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.calendar.CalenderActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

public class ProjectCreateActivity extends BaseActivity {

    private TitleBar mProjectCreateTitleBar;
    private MultiLineEditText mProjectCreateProjectName;
    private MultiLineEditText mProjectCreateProjectContent;
    private RoundButton mProjectCreateMaxDateButton;


    private static final int PICK_DATE_REQUEST = 0;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常情况
                    // TODO
                    break;
                case 2: // 正常情况
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
        }
    }
}