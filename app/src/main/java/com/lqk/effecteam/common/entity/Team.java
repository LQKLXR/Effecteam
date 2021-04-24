package com.lqk.effecteam.common.entity;

import java.util.Date;

/**
 * Create By LiuQK on 2021/3/30
 * Describe: 团队的 Model
 */
public class Team {
    private int id;
    private String number;
    private String name;
    private String teamInfo;
    private String institution;
    private Date createTime;
    /* 0表示存在， 1表示解散*/
    private int status;

    public Team(int id, String number, String name, String teamInfo, String institution, Date createTime, int status) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.teamInfo = teamInfo;
        this.institution = institution;
        this.createTime = createTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
