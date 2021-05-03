package com.lqk.effecteam.common.entity.notice;


import java.util.Date;

/**
 * @author lqk
 * @Date 2021/4/27
 * @Description 申请加入队伍的通知
 */
public class ApplyNotice extends SimpleNotice{

    public static final int ACCEPTED = 1;
    public static final int REFUSED = 2;

    private int senderId;
    private int teamId;

    public ApplyNotice(int id, String content, int userId, int status, Date date, int senderId, int teamId) {
        super(id, content, userId, status, date);
        this.senderId = senderId;
        this.teamId = teamId;
    }

    public ApplyNotice() {
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
}
