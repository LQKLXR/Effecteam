package com.lqk.effecteam.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    private TitleBar mRegisterTitleBar;
    private MultiLineEditText mRegisterInputActualname;
    private MultiLineEditText mRegisterInputEmail;
    private EditText mRegisterInputPswd;
    private EditText mRegisterInputPswdRe;
    private SmoothCheckBox mRegisterCheckNan;
    private SmoothCheckBox mRegisterCheckNv;




    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(RegisterActivity.this, "请尽快输入邮箱验证码", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        mRegisterTitleBar = findViewById(R.id.register_title_bar);
        mRegisterInputActualname = findViewById(R.id.register_input_actualname);
        mRegisterInputEmail = findViewById(R.id.register_input_email);
        mRegisterInputPswd = findViewById(R.id.register_input_pswd);
        mRegisterInputPswdRe = findViewById(R.id.register_input_pswd_re);
        mRegisterCheckNan = findViewById(R.id.register_check_nan);
        mRegisterCheckNv = findViewById(R.id.register_check_nv);

        addListener();
    }

    private void addListener() {
        /* 退出这个Activity */
        mRegisterTitleBar.setLeftClickListener(v -> {
            finish();
        });
        mRegisterTitleBar.addAction(new TitleBar.TextAction("注册") {
            @Override
            public void performAction(View view) {

                if (mRegisterInputPswd.getText().toString().length() == 0
                        || mRegisterInputActualname.getContentText().length() == 0
                        || mRegisterInputEmail.getContentText().length() == 0
                        || ((!mRegisterCheckNan.isChecked()) && (!mRegisterCheckNv.isChecked()))) {
                    Toast.makeText(RegisterActivity.this, "缺少必要内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mRegisterInputPswd.getText().toString().equals(mRegisterInputPswdRe.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                mRegisterCheckNan.setOnCheckedChangeListener((checkBox, isChecked) -> {
                    if(isChecked){
                        mRegisterCheckNv.setChecked(false);
                    }
                });
                mRegisterCheckNv.setOnCheckedChangeListener((checkBox, isChecked) -> {
                    if(isChecked){
                        mRegisterCheckNan.setChecked(false);
                    }
                });

                // 获得性别
                String gender = "男";
                // 注册
                if (mRegisterCheckNv.isChecked()){
                    gender = "女";
                }

                FormBody formBody = new FormBody.Builder().add("email", mRegisterInputEmail.getContentText())
                        .add("password", mRegisterInputPswd.getText().toString())
                        .add("actualName", mRegisterInputActualname.getContentText())
                        .add("gender", gender).build();

                HttpUtil.connectInternet("register", formBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if ((new String(response.body().bytes())).equals("success")){
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = "注册成功";
                            handler.sendMessage(message);
                        }
                    }
                });
                // 结束注册

            }
        });

    }
}