package com.lqk.effecteam.team;

import com.lqk.effecteam.team.chat.Message;
import com.lqk.effecteam.team.list.Team;

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


    static {
        teamArrayList.add(new Team(1,"111","毕设小组","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(2,"222","后端开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(3,"333","Android开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(4,"444","Unity开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(5,"555","H5开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(6,"666","PHP开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(7,"777","CV开发团队","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(8,"888","测试小组","冲冲冲","华科软院17级"));
        teamArrayList.add(new Team(9,"999","GoLang开发团队","冲冲冲","华科软院17级"));
    }

    static {
        userArrayList.add(new User(1,"刘乾坤","刘乾坤",1,"Universe_Liu@163.com","11111"));
        userArrayList.add(new User(2,"周杰伦","周杰伦",1,"14141441@163.com","11111"));
        userArrayList.add(new User(3,"林俊杰","林俊杰",1,"131313@163.com","11111"));
    }

    static {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    messageArrayList.add(new Message(1,1,1,"哈哈哈啊哈，牛牛牛",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(2,2,1,"What are you saying",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(3,3,1,"你们都给我老实点",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(4,2,1,"呦呦呦，不得了了，不得了了",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(5,3,1,"给爷整笑了",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(6,1,1,"请开始你们的表演",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(7,2,1,"哼",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(8,3,1,"你想说什么",new Date(System.currentTimeMillis())));
                    TimeUnit.SECONDS.sleep(1);
                    messageArrayList.add(new Message(9,1,1,"你猜",new Date(System.currentTimeMillis())));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }


}
