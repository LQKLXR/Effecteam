package com.lqk.effecteam.task.create;

/**
 * Create By LiuQK on 2021/4/14
 * Describe: 供选择的单元
 */
public class PickItem {

    private int id;
    private String name;

    public PickItem(int id, String name) {
        this.id = id;
        this.name = name;
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
}
