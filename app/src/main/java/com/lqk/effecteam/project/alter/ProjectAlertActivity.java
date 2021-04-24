package com.lqk.effecteam.project.alter;

import android.os.Bundle;
import android.view.View;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

public class ProjectAlertActivity extends BaseActivity {


    private TitleBar mProjectAlterTitleBar;
    private MultiLineEditText mProjectAlterProjectName;
    private MultiLineEditText mProjectAlterProjectContent;
    private RoundButton mProjectAlterMaxDateButton;

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

        mProjectAlterTitleBar.setLeftClickListener(v -> finish());
        mProjectAlterTitleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                // TODO
            }
        });

        mProjectAlterProjectName.setContentText("原名称");
        mProjectAlterProjectContent.setContentText("原介绍");
        mProjectAlterMaxDateButton.setText("原日期");
    }


}