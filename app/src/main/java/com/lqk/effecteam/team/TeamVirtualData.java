package com.lqk.effecteam.team;

import com.lqk.effecteam.project.list.Project;
import com.lqk.effecteam.task.create.PickItem;
import com.lqk.effecteam.team.chat.Message;
import com.lqk.effecteam.team.list.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Create By LiuQK on 2021/4/4
 * Describe:  TODO 虚拟数据
 */
public class TeamVirtualData {

    /*虚拟的团队列表数据*/
    public static ArrayList<Team> teamArrayList = new ArrayList<>();
    /*虚拟的用户列表*/
    public static ArrayList<User> userArrayList = new ArrayList<>();
    /*虚拟的消息列表数据*/
    public static ArrayList<Message> messageArrayList = new ArrayList<>();
    /*虚假的选择人员列表*/
    public static ArrayList<PickItem> pickUserArrayList = new ArrayList<>();
    /*虚假的选择文档列表*/
    public static ArrayList<PickItem> pickDocArrayList = new ArrayList<>();


    static {
        teamArrayList.add(new Team(0,"111","毕设小组","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(1,"222","后端开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(2,"333","Android开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(3,"444","Unity开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(4,"555","H5开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(5,"666","PHP开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(6,"777","CV开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(7,"888","测试小组","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(8,"999","GoLang开发团队","冲冲冲","华科软院17级"));
    }

    static {
        userArrayList.add(new User(0,"刘乾坤","刘乾坤",1,"Universe_Liu@163.com","11111"));
        userArrayList.add(new User(1,"周杰伦","周杰伦",1,"14141441@163.com","11111"));
        userArrayList.add(new User(2,"林俊杰","林俊杰",1,"131313@163.com","11111"));
        userArrayList.add(new User(3,"王力宏","林俊杰",1,"131313@163.com","11111"));
        userArrayList.add(new User(4,"周传雄","林俊杰",1,"131313@163.com","11111"));
        userArrayList.add(new User(5,"林夕","林俊杰",1,"131313@163.com","11111"));
        userArrayList.add(new User(6,"啊哈哈","林俊杰",1,"131313@163.com","11111"));
    }

    static {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse("2021-03-02 14:55:20");
            Date date2 = sdf.parse("2021-03-03 15:33:22");
            Date date3 = sdf.parse("2021-03-04 16:24:11");
            Date date4 = sdf.parse("2021-03-04 16:25:11");
            Date date5 = sdf.parse("2021-03-04 16:26:11");
            Date date6 = sdf.parse("2021-03-04 16:27:11");
            Date date7 = sdf.parse("2021-03-04 16:28:11");
            Date date8 = sdf.parse("2021-03-04 16:29:11");
            Date date9 = sdf.parse("2021-03-04 16:20:11");

            messageArrayList.add(new Message(0,0,0,"哈哈哈啊哈，牛牛牛",date1));
            messageArrayList.add(new Message(1,1,0,"What are you saying",date2));
            messageArrayList.add(new Message(2,2,0,"你们都给我老实点",date3));
            messageArrayList.add(new Message(3,1,0,"呦呦呦，不得了了，不得了了",date4));
            messageArrayList.add(new Message(4,2,0,"给爷整笑了",date5));
            messageArrayList.add(new Message(5,0,0,"请开始你们的表演",date6));
            messageArrayList.add(new Message(6,1,0,"哼",date7));
            messageArrayList.add(new Message(7,2,0,"你想说什么",date8));
            messageArrayList.add(new Message(8,0,0,"你猜",date9));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        for (User u: userArrayList) {
            pickUserArrayList.add(new PickItem(u.getId(), u.getUserName()));
        }
    }

    static {
        for (int i = 0; i < 10; i++) {
            pickDocArrayList.add(new PickItem(i, "测试文档" + i));
        }
    }

}
