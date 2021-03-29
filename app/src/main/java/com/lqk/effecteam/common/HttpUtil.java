package com.lqk.effecteam.common;

import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lqk.effecteam.account.AccountActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/3/24
 * Describe: 帮助连接网络的工具类
 */
public class HttpUtil {

    public static String ServerIP = "http://123.57.177.134:8080/";


    public static void connectInternet(String urlString, @Nullable RequestBody requestBody, Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = null;
                if(requestBody == null){
                    request = new Request.Builder().url(urlString).get().build();
                }
                else {
                    request = new Request.Builder().url(urlString).post(requestBody).build();
                }
                Call call = okHttpClient.newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }



}
