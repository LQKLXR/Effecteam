package com.lqk.effecteam.common.entity;

import java.util.Date;

/**
 * @author lqk
 * @Date 2021/4/27
 * @Description 聊天的消息
 */
public class ChatMessage {

    private int id;
    private String content;
    private int teamId;
    private int senderId;
    private Date date;

    public ChatMessage(int id, String content, int teamId, int senderId, Date date) {
        this.id = id;
        this.content = content;
        this.teamId = teamId;
        this.senderId = senderId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
