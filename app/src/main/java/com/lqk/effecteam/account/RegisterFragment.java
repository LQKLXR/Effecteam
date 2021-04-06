package com.lqk.effecteam.account;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/3/23
 * Describe: 注册用的Fragment
 */
public class RegisterFragment extends Fragment {

    private EditText mInputPhoneNumber;
    private EditText mInputPassword;
    private EditText mReInputPassword;
    private Button mRegisterButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        /*绑定ID*/
        mInputPhoneNumber = view.findViewById(R.id.input_phoneNumber_register_edit);
        mInputPassword = view.findViewById(R.id.input_password_register_edit);
        mReInputPassword = view.findViewById(R.id.input_password_register_edit_re);
        mRegisterButton = view.findViewById(R.id.register_button);
        addListener();
        return view;
    }

    /**
     * 为组件加上各种事件监听器
     */
    private void addListener() {
        /*在电话号码处，输入回车，进入密码处*/
        mInputPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mInputPassword.requestFocus();
                    mInputPassword.setSelection(mInputPassword.getText().length());
                    return true;
                }
                return false;
            }
        });
        /*在密码处，输入回车，进入重复密码处*/
        mInputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mReInputPassword.requestFocus();
                    mReInputPassword.setSelection(mInputPassword.getText().length());
                    return true;
                }
                return false;
            }
        });
        /*在重复密码栏，输入回车，就是直接登录*/
        mReInputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mRegisterButton.callOnClick();
                    return true;
                }
                return false;
            }
        });
        /*注册按钮的事件监听器*/
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordString = mInputPassword.getText().toString();
                String rePasswordString = mReInputPassword.getText().toString();
                if (passwordString.equals(rePasswordString)) {
                    Toast.makeText(getActivity(), "两次密码输入不一致！", Toast.LENGTH_SHORT);
                    return;
                }
                String phoneNumberString = mInputPhoneNumber.getText().toString();
                /* TODO 注册时候的网络接口处理 */
                FormBody formBody = new FormBody.Builder().add("", phoneNumberString).add("", passwordString).build();
                String urlString = "";
                HttpUtil.connectInternet(urlString, formBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        /*响应失败*/
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        /*响应成功*/
                    }
                });
            }
        });
    }
}
