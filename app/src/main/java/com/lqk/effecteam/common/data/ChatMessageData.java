package com.lqk.effecteam.common.data;


import java.util.Date;

/**
 * @author lqk
 * @Date 2021/4/30
 * @Description
 */
public class ChatMessageData {
    private int id;
    private String content;
    private int teamId;
    private int senderId;
    private String senderName;
    private Date date;

    public ChatMessageData() {
    }

    public ChatMessageData(int id, String content, int teamId, int senderId, String senderName, Date date) {
        this.id = id;
        this.content = content;
        this.teamId = teamId;
        this.senderId = senderId;
        this.senderName = senderName;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
