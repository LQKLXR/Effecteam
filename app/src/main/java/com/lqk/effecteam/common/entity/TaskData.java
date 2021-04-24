package com.lqk.effecteam.common.entity;

import java.util.List;

/**
 * Create By LiuQK on 2021/4/23
 * Describe:
 */
public class TaskData {
    private int userId;
    private int projectId;
    private String name;
    private String content;
    private int priority;
    private String maxDateString;
    private List<Integer> userIdList;
    private List<Integer> docIdList;

    public TaskData() {
    }

    public TaskData(int userId, int projectId, String name, String content, int priority, String maxDateString, List<Integer> userIdList, List<Integer> docIdList) {
        this.userId = userId;
        this.projectId = projectId;
        this.name = name;
        this.content = content;
        this.priority = priority;
        this.maxDateString = maxDateString;
        this.userIdList = userIdList;
        this.docIdList = docIdList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public String getMaxDateString() {
        return maxDateString;
    }

    public void setMaxDateString(String maxDateString) {
        this.maxDateString = maxDateString;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public List<Integer> getDocIdList() {
        return docIdList;
    }

    public void setDocIdList(List<Integer> docIdList) {
        this.docIdList = docIdList;
    }
}
