package com.lqk.effecteam.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.MainActivity;
import com.lqk.effecteam.common.entity.User;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/3/23
 * Describe: 登录用到的Fragment
 */
public class LoginFragment extends Fragment {

    /*输入的手机号*/
    private EditText mInputPhoneNumber;
    /*输入的密码*/
    private EditText mInputPasswordText;
    /*点击登录按钮*/
    private Button mLoginButton;
    /*去注册文字*/
    private TextView mToRegisterText;

    /*加载转圈*/
    private MaterialDialog mLoadingDialog;


    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    mLoadingDialog.dismiss();
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    mLoadingDialog.dismiss();
                    Toast.makeText(getActivity(), "帐号或密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    User user = (User) msg.obj;
                    editor.putInt("userId", user.getId());
                    editor.putString("actualName", user.getActualName());
                    editor.putString("gender", user.getGender());
                    editor.commit();

                    mLoadingDialog.dismiss();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("userId", user.getId());
                    intent.putExtra("actualName", user.getActualName());
                    startActivity(intent);
                    getActivity().finish();
                    break;
            }
        }
    };

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mInputPhoneNumber = view.findViewById(R.id.input_phoneNumber_edit);
        mInputPasswordText = view.findViewById(R.id.input_password_edit);
        mLoginButton = view.findViewById(R.id.login_button);
        mToRegisterText = view.findViewById(R.id.to_register_text);

        mLoadingDialog = new MaterialDialog.Builder(getActivity()).content(R.string.loginDialog)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();

        /*点击登录按钮，进入主界面*/
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("登录Fragment", "onClick: 点击了登录");
                login();
            }
        });
        /*点击去注册文字*/
        mToRegisterText.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivityForResult(intent, 1);
        });
        /*在手机号处点击回车键*/
        mInputPhoneNumber.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                mInputPasswordText.requestFocus();
                mInputPasswordText.setSelection(mInputPasswordText.getText().length());
                return true;
            }
            return false;
        });

        return view;
    }

    /**
     *
     */
    private void login() {
        //开启加载进度条
        mLoadingDialog.show();

        FormBody formBody = new FormBody.Builder().add("email", mInputPhoneNumber.getText().toString())
                .add("password", mInputPasswordText.getText().toString()).build();
        HttpUtil.connectInternet("login", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = (new String(response.body().bytes()));
                if (body.length() == 0){
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
                }
                else {
                    Gson gson = new Gson();
                    User user = gson.fromJson(body, User.class);
                    Message message = Message.obtain();
                    message.what = 3;
                    message.obj = user;
                    handler.sendMessage(message);
                }
            }
        });
    }

}