package com.lqk.effecteam.project.dynamic;

import java.util.Date;

/**
 * Create By LiuQK on 2021/4/16
 * Describe:
 */
public class Dynamic {

    private int id;
    private int userId;
    private String action;
    private String object;
    private Date date;

    public Dynamic(int id, int userId, String action, String object, Date date) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.object = object;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
