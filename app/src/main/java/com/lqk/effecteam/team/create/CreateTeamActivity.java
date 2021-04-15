package com.lqk.effecteam.team.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;

/**
 * 创建队伍的时候显示用的Activity
 */
public class CreateTeamActivity extends BaseActivity {

    /*创建队伍标题栏*/
    private TitleBar mTitleBar;
    /*团队名称输入框*/
    private EditText mTeamName;
    /*团队机构输入框*/
    private EditText mTeamOrganization;
    /*团队简介*/
    private EditText mTeamInfo;

    public static final int BACK_TO_TEAM_RESULT = 3;

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
        mTitleBar = findViewById(R.id.create_team_title_bar);
        mTeamName = findViewById(R.id.create_team_input_name_text);
        mTeamOrganization = findViewById(R.id.create_team_input_org_text);
        mTeamInfo = findViewById(R.id.create_team_input_info_text);
        addListener();
    }

    /**
     * 绑定监听器
     */
    private void addListener(){
        /*标题栏设定*/
        mTitleBar.setLeftClickListener(v -> {
            //跳转到主Activity，结束创建团队Activity
            Intent intent = new Intent();
            setResult(BACK_TO_TEAM_RESULT);
            finish();
        }).addAction(new TitleBar.TextAction("确定"){
            @Override
            public void performAction(View view) {
                //TODO 创建团队的网络接口
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