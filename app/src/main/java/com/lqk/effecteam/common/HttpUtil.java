package com.lqk.effecteam.common;

import androidx.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Create By LiuQK on 2021/3/24
 * Describe: 帮助连接网络的工具类
 */
public class HttpUtil {

    public static String ServerIP = "http://192.168.254.1:8080/";
    public static String Shared_File_Name = "EffecteamValues";
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static void connectInternet(String urlString, @Nullable RequestBody requestBody, Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = null;
                if(requestBody == null){
                    request = new Request.Builder().url(ServerIP + urlString).get().build();
                }
                else {
                    request = new Request.Builder().url(ServerIP + urlString).post(requestBody).build();
                }
                Call call = okHttpClient.newCall(request);

                call.enqueue(callback);

            }
        }).start();
    }



}
