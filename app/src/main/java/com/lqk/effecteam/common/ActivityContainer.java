package com.lqk.effecteam.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/22
 * Describe: 单例类-用于储存该应用下所有的Activity
 */
public class ActivityContainer {

    /*懒汉式初始化*/
    private static List<Activity> activityList = new ArrayList<>();

    private ActivityContainer() {
    }

    /**
     * 增加一个Activity
     * @param activity 要添加的activity
     */
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 移除一个Activity
     * @param activity 要移除的Activity
     */
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    /**
     * 摧毁全部的Activity
     * @return
     */
    public static boolean destroyAllActivities(){
        for (Activity activity :activityList) {
            activity.finish();
        }
        /*解除引用*/
        activityList.clear();
        return true;
    }

}
