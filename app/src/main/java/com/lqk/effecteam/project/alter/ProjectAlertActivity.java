package com.lqk.effecteam.project.alter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.calendar.CalenderActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.project.create.ProjectCreateActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class ProjectAlertActivity extends BaseActivity {


    private TitleBar mProjectAlterTitleBar;
    private MultiLineEditText mProjectAlterProjectName;
    private MultiLineEditText mProjectAlterProjectContent;
    private RoundButton mProjectAlterMaxDateButton;
    private String maxDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_project);
        initView();
    }

    private void initView() {
        mProjectAlterTitleBar = findViewById(R.id.project_alert_title_bar);
        mProjectAlterProjectName = findViewById(R.id.project_alert_project_name);
        mProjectAlterProjectContent = findViewById(R.id.project_alert_project_content);
        mProjectAlterMaxDateButton = findViewById(R.id.project_alert_max_date_button);

        addListener();
    }

    private void addListener() {

        int projectId = getIntent().getIntExtra("projectId", 0);
        String projectName = getIntent().getStringExtra("projectName");
        String projectEndDate = getIntent().getStringExtra("projectEndDate");
        String projectInfo = getIntent().getStringExtra("projectInfo");
        mProjectAlterProjectName.setContentText(projectName);
        mProjectAlterProjectContent.setContentText(projectInfo);
        mProjectAlterMaxDateButton.setText(projectEndDate);

        mProjectAlterMaxDateButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectAlertActivity.this, CalenderActivity.class);
            intent.putExtra("type", 0);
            startActivityForResult(intent, 0);
        });

        mProjectAlterTitleBar.setLeftClickListener(v -> finish());
        mProjectAlterTitleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                String url = "alterProject";
                FormBody formBody = new FormBody.Builder().add("projectId", String.valueOf(projectId))
                        .add("name", mProjectAlterProjectName.getContentText())
                        .add("info", mProjectAlterProjectContent.getContentText())
                        .add("maxDate", maxDateString).build();
                HttpUtil.connectInternet(url, formBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = new String(response.body().bytes());
                        if (body.equals("success")){
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String dateString = data.getStringExtra("dateString");
            mProjectAlterMaxDateButton.setText("已选择 " + dateString);
            this.maxDateString = dateString;
        }
    }

}