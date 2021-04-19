package com.lqk.effecteam.project;

import com.lqk.effecteam.project.dynamic.Dynamic;
import com.lqk.effecteam.project.list.Project;
import com.lqk.effecteam.task.Task;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/7
 * Describe: TODO 项目列表的虚假数据
 */
public class ProjectVirtualData {

    public static List<Project> projectList;

    public static List<Task> taskList;

    public static List<Dynamic> dynamicList;

    public static List<File> fileList;

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date11 = sdf.parse("2021-03-02 14:55:20");
            Date date21 = sdf.parse("2021-03-03 15:33:22");
            Date date31 = sdf.parse("2021-03-04 16:22:11");

            Date date12 = sdf.parse("2021-05-02 14:55:20");
            Date date22 = sdf.parse("2021-04-13 15:33:22");
            Date date32 = sdf.parse("2021-07-04 16:22:11");

            projectList = new ArrayList<>();
            projectList.add(new Project(0, "基于安卓的测试项目", 0, date11, null, date12));
            projectList.add(new Project(1, "呵呵哈项目", 0, date11, null, date12));
            projectList.add(new Project(2, "后端开发项目", 1, date21, null, date22));
            projectList.add(new Project(3, "前端开发项目", 1, date31, null, date32));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date date11 = sdf.parse("2021-03-02 14:55:20");
            Date date21 = sdf.parse("2021-03-03 15:33:22");
            Date date31 = sdf.parse("2021-03-04 16:22:11");
            Date date41 = sdf.parse("2021-03-05 14:55:20");
            Date date51 = sdf.parse("2021-03-06 15:33:22");

            Date date12 = sdf.parse("2021-05-02 14:55:20");
            Date date22 = sdf.parse("2021-04-12 15:33:22");
            Date date32 = sdf.parse("2021-07-02 16:22:11");
            Date date42 = sdf.parse("2021-06-12 15:33:22");
            Date date52 = sdf.parse("2021-04-20 16:22:11");
            taskList = new ArrayList<>();
            taskList.add(new Task(0, "需求探讨", "完成需求的大致探讨和框架", date11, null, date12, 1, null ,null));
            taskList.add(new Task(1, "架构设计", "完成架构设计和文档编写", date21, null, date22, 1, null ,null));
            taskList.add(new Task(2, "开发阶段1", "建立基本的项目模块", date31, null, date32, 2, null ,null));
            taskList.add(new Task(3, "开发阶段2", "完成团队模块的实现", date41, null, date42, 2, null ,null));
            taskList.add(new Task(4, "上报进度", "上报进度完成情况", date51, null, date52, 3, null ,null));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    static {
        dynamicList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date11 = sdf.parse("2021-03-03 14:55:20");
            Date date21 = sdf.parse("2021-03-04 15:33:22");
            Date date31 = sdf.parse("2021-03-05 16:22:11");
            Date date41 = sdf.parse("2021-03-06 14:55:20");
            Date date51 = sdf.parse("2021-03-07 15:33:22");

            dynamicList.add(new Dynamic(0,0,"上传了","文档1",date11));
            dynamicList.add(new Dynamic(0,0,"完成了","任务1",date21));
            dynamicList.add(new Dynamic(0,0,"上传了","文档2",date31));
            dynamicList.add(new Dynamic(0,0,"上传了","文档3",date41));
            dynamicList.add(new Dynamic(0,0,"上传了","文档4",date51));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }




}
