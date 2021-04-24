package com.lqk.effecteam.team.create;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.team.join.JoinTeamActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    /*加载转圈*/
    private MaterialDialog mMaterialDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mMaterialDialog.dismiss();
                    Toast.makeText(CreateTeamActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mMaterialDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("toast", "创建成功");
                    setResult(BACK_TO_TEAM_RESULT, intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        initView();
    }

    /**
     * 绑定各个组件ID
     */
    private void initView() {
        mMaterialDialog = new MaterialDialog.Builder(CreateTeamActivity.this).content(R.string.loadingDialog)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();
        mTitleBar = findViewById(R.id.create_team_title_bar);
        mTeamName = findViewById(R.id.create_team_input_name_text);
        mTeamOrganization = findViewById(R.id.create_team_input_org_text);
        mTeamInfo = findViewById(R.id.create_team_input_info_text);
        addListener();
    }

    /**
     * 绑定监听器
     */
    private void addListener() {
        /*标题栏设定*/
        mTitleBar.setLeftClickListener(v -> {
            //跳转到主Activity，结束创建团队Activity
            Intent intent = new Intent();
            setResult(BACK_TO_TEAM_RESULT);
            finish();
        }).addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                mMaterialDialog.show();
                /* 获得输入信息 */
                String name = mTeamName.getText().toString();
                String info = mTeamInfo.getText().toString();
                String institution = mTeamOrganization.getText().toString();
                /* 获得当前用户信息 */
                SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", 0);
                /* 开始连接网络 */
                String url = "createTeam?name=" + name + "&info=" + info + "&institution=" + institution+"&userId="+userId;
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
                        if (body.equals("success")){
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });

        /*绑定输入框监听器*/
        mTeamName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //按下了回车键，进入机构的填写
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
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
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mTeamInfo.requestFocus();
                    mTeamInfo.setSelection(mTeamInfo.getText().length());
                }
                return false;
            }
        });

    }
}