package com.lqk.effecteam.team.chat;

import java.util.Date;

/**
 * Create By LiuQK on 2021/4/4
 * Describe: 聊天的数据
 */
public class Message {

    /*消息ID*/
    private int id;
    /*发送者的ID*/
    private int senderId;
    /*团队ID*/
    private int teamId;
    /*消息内容*/
    private String content;
    /*发送时间*/
    private Date date;

    public Message() {
    }

    public Message(int id, int senderId, int teamId, String content, Date date) {
        this.id = id;
        this.senderId = senderId;
        this.teamId = teamId;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
