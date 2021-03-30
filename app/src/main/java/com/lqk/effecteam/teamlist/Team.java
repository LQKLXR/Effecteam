package com.lqk.effecteam.teamlist;

/**
 * Create By LiuQK on 2021/3/30
 * Describe: 团队的 Model
 */
public class Team {
    /*团队ID*/
    private String teamId;
    /*团队号码*/
    private String teamNumber;
    /*团队名称*/
    private String teamName;
    /*团队简介*/
    private String teamInfo;
    /*团队机构*/
    private String teamOrganization;

    public Team(String teamId, String teamNumber, String teamName, String teamInfo, String teamOrganization) {
        this.teamId = teamId;
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.teamOrganization = teamOrganization;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }

    public String getTeamOrganization() {
        return teamOrganization;
    }

    public void setTeamOrganization(String teamOrganization) {
        this.teamOrganization = teamOrganization;
    }
}
