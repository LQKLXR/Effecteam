package com.lqk.effecteam.project.list;

import java.util.Date;

/**
 * Create By LiuQK on 2021/4/7
 * Describe: 项目的类
 */
public class Project {

    private int id;
    private String name;
    private int ownerTeamId;
    private Date startDate;
    private Date endDate;
    private Date maxDate;

    public Project() {
    }

    public Project(int id, String name, int ownerTeamId, Date startDate, Date endDate, Date maxDate) {
        this.id = id;
        this.name = name;
        this.ownerTeamId = ownerTeamId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxDate = maxDate;
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

    public int getOwnerTeamId() {
        return ownerTeamId;
    }

    public void setOwnerTeamId(int ownerTeamId) {
        this.ownerTeamId = ownerTeamId;
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
}
