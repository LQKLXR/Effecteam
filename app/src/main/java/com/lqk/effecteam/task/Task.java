package com.lqk.effecteam.task;

import java.util.Date;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/14
 * Describe:
 */
public class Task {

    private int id;
    private String name;
    private String content;
    private Date startTime;
    private Date endTime;
    private Date maxTime;
    private int priority;
    private List<String> referenceDocs;
    private List<String> resultDocs;

    public Task(int id, String name, String content, Date startTime, Date endTime, Date maxTime, int priority, List<String> referenceDocs, List<String> resultDocs) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxTime = maxTime;
        this.priority = priority;
        this.referenceDocs = referenceDocs;
        this.resultDocs = resultDocs;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getReferenceDocs() {
        return referenceDocs;
    }

    public void setReferenceDocs(List<String> referenceDocs) {
        this.referenceDocs = referenceDocs;
    }

    public List<String> getResultDocs() {
        return resultDocs;
    }

    public void setResultDocs(List<String> resultDocs) {
        this.resultDocs = resultDocs;
    }
}
