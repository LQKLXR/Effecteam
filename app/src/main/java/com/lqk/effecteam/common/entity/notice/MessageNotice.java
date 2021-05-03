package com.lqk.effecteam.common.entity.notice;


import java.util.Date;

/**
 * @author lqk
 * @Date 2021/4/28
 * @Description
 */
public class MessageNotice extends SimpleNotice{

    public static final int READED = 1;

    private int teamId;

    public MessageNotice() {
    }

    public MessageNotice(int id, String content, int userId, int status, Date date, int teamId) {
        super(id, content, userId, status, date);
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
