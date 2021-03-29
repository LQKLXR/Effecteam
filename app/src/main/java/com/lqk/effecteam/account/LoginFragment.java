package com.lqk.effecteam.account;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
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

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/3/23
 * Describe: 登录用到的Fragment
 */
public class LoginFragment extends Fragment{

    /*输入的手机号*/
    private EditText mInputPhoneNumber;
    /*输入的密码*/
    private EditText mInputPasswordText;
    /*点击登录按钮*/
    private Button mLoginButton;
    /*去注册文字*/
    private TextView mToRegisterText;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1 :
                    Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
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

        /*点击登录按钮，进入主界面*/
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("登录Fragment", "onClick: 点击了登录");
                login(mInputPhoneNumber.getText().toString(), mInputPasswordText.getText().toString());
            }
        });
        /*点击去注册文字*/
        mToRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_login, registerFragment).commit();
            }
        });

        mInputPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    mInputPasswordText.requestFocus();
                    mInputPasswordText.setSelection(mInputPasswordText.getText().length());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    /**
     * 登录的处理
     * @param userName
     * @param password
     */
    private void login(String userName, String password){
         new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("inputPhoneNumber", "15207155054").add("inputPassword", "lvxinru521").build();
                Request request = new Request.Builder().url(HttpUtil.ServerIP + "login").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = new String(response.body().bytes());
                        if(result.equals("true")){
                            Message message = Message.obtain();
                            message.what = 1;
                            Log.d("登录Fragment", "onResponse: 登录成功");
                            handler.sendMessage(message);
                        }
                        else{
                            Message message = Message.obtain();
                            message.what = 2;
                            Log.d("登录Fragment", "onResponse: 登录失败");
                            handler.sendMessage(message);
                        }
                    }
                });

            }
        }).start();
    }

}