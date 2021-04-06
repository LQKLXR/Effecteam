package com.lqk.effecteam.team.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.main.MainActivity;

/**
 * 创建队伍的时候显示用的Activity
 */
public class CreateTeamActivity extends BaseActivity {

    /*返回按钮*/
    private ImageButton mBackButton;
    /*团队名称输入框*/
    private EditText mTeamName;
    /*团队机构输入框*/
    private EditText mTeamOrganization;
    /*团队简介*/
    private EditText mTeamInfo;
    /*确定按钮*/
    private Button mConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        initView();
    }

    /**
     * 绑定各个组件ID
     */
    private void initView(){
        mBackButton = findViewById(R.id.create_team_back_button);
        mTeamName = findViewById(R.id.create_team_input_name_text);
        mTeamOrganization = findViewById(R.id.create_team_input_org_text);
        mTeamInfo = findViewById(R.id.create_team_input_info_text);
        mConfirmButton = findViewById(R.id.create_team_confirm_button);
        addListener();
    }

    /**
     * 绑定监听器
     */
    private void addListener(){
        /*绑定按钮监听器*/
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主Activity，结束创建团队Activity
                Intent intent = new Intent(CreateTeamActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO 创建团队提交的网络接口编写 */
            }
        });
        /*绑定输入框监听器*/
        mTeamName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //按下了回车键，进入机构的填写
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    mTeamOrganization.requestFocus();
                    mTeamOrganization.setSelection(mTeamOrganization.getText().length());
                }
                return false;
            }
        });
        mTeamOrganization.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //按下了回车键，进入简介的填写
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    mTeamInfo.requestFocus();
                    mTeamInfo.setSelection(mTeamInfo.getText().length());
                }
                return false;
            }
        });

    }
}