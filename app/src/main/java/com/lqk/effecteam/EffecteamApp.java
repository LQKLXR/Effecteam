package com.lqk.effecteam;

import android.app.Application;
import android.util.Log;

import com.xuexiang.xui.XUI;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

/**
 * Create By LiuQK on 2021/4/6
 * Describe:  XUI框架初始化
 */
public class EffecteamApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //设置北京时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        //初始化UI框架
        XUI.init(this);
        //开启UI框架调试日志
        XUI.debug(true);

    }
}
