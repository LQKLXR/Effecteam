package com.lqk.effecteam.team;

/**
 * Create By LiuQK on 2021/4/5
 * Describe:
 */
public class User {
    /*用户ID*/
    private int id;

    private String userName;

    private String actualName;

    private int gender;

    private String email;

    private String phone;

    public User() {
    }

    public User(int id, String userName, String actualName, int gender, String email, String phone) {
        this.id = id;
        this.userName = userName;
        this.actualName = actualName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
