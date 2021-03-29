package com.lqk.effecteam.common;

/**
 * Create By LiuQK on 2021/3/25
 * Describe:
 */
public class Status<T> {

    private String statusName;
    private T  statusValue;

    public Status(String statusName, T statusValue) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }

    public Status() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public T getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(T statusValue) {
        this.statusValue = statusValue;
    }
}
