package com.lqk.effecteam.common.data;


/**
 * @author lqk
 * @Date 2021/4/28
 * @Description 消息通知的传输数据类
 */
public class MessageNoticeData {

    private int id;
    private String content;
    private int userId;
    private int status;
    private int teamId;
    private String teamName;

    public MessageNoticeData() {
    }

    public MessageNoticeData(int id, String content, int userId, int status, int teamId, String teamName) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.status = status;
        this.teamId = teamId;
        this.teamName = teamName;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
