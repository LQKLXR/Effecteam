package com.lqk.effecteam.common.entity;

import java.util.Date;

/**
 * Create By LiuQK on 2021/4/14
 * Describe:
 */
public class Task {

    private int id;
    private String name;
    private String content;
    private int priority;
    private Date maxDate;
    private Date startDate;
    private Date endDate;
    private int projectId;
    /* 0正在进行, 1已完成, 2已删除*/
    private int status;

    /*输入文档、输出文档、人员选择是额外的表*/

    public Task(int id, String name, String content, int priority, Date maxDate, Date startDate, Date endDate, int projectId, int status) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.priority = priority;
        this.maxDate = maxDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
