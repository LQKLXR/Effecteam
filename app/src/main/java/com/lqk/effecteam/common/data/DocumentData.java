package com.lqk.effecteam.common.data;

/**
 * Create By LiuQK on 2021/4/25
 * Describe:
 */
public class DocumentData {

    private int id;
    private String originalName;
    private String newName;
    private String path;
    private int projectId;
    private String projectName;

    public DocumentData(int id, String originalName, String newName, String path, int projectId, String projectName) {
        this.id = id;
        this.originalName = originalName;
        this.newName = newName;
        this.path = path;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}