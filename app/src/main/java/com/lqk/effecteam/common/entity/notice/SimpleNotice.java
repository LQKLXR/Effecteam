package com.lqk.effecteam.common.entity.notice;


import java.util.Date;

/**
 * @author lqk
 * @Date 2021/4/28
 * @Description
 */
public class SimpleNotice {

    public static final int DOING = 0;
    public static final int READED = 1;

    private int id;
    private String content;
    private int userId;
    private int status;
    private Date date;

    public SimpleNotice() {
    }

    public SimpleNotice(int id, String content, int userId, int status, Date date) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
