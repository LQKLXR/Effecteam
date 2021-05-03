package com.lqk.effecteam.common.entity;

import java.util.Date;

/**
 * Create By LiuQK on 2021/4/7
 * Describe: 项目的类
 */
public class Project {

    private int id;
    private String name;
    private String info;
    private Date startDate;
    private Date endDate;
    private Date maxDate;
    private int teamId;
    private String teamName;
    /*0表示正在进行，1表示已经归档，2表示已经删除*/
    private int status;

    public Project(int id, String name, String info, Date startDate, Date endDate, Date maxDate, int teamId, String teamName, int status) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDate = maxDate;
        this.teamId = teamId;
        this.teamName = teamName;
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
