package com.lqk.effecteam.common.entity;

/**
 * Create By LiuQK on 2021/4/5
 * Describe:
 */
public class User {

    private int id;
    private String actualName;
    private String gender;
    /* 帐号登录相关 */
    private String email;
    private String password;

    public User(int id, String actualName, String gender, String email, String password) {
        this.id = id;
        this.actualName = actualName;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
