package com.lqk.effecteam.common.comparator;

import com.lqk.effecteam.common.entity.Project;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Create By LiuQK on 2021/4/20
 * Describe: 项目的排序器
 */
public class ProjectComparator implements Comparator<Project> {

    private String type;
    private Comparator<Project> comparator;

    private StartTimeComparator startTimeComparator = new StartTimeComparator();
    private MaxTimeComparator maxTimeComparator = new MaxTimeComparator();
    private TeamNameComparator teamNameComparator = new TeamNameComparator();

    public ProjectComparator(String type) {
        this.type = type;
        setComparator();
    }

    public void setType(String type) {
        this.type = type;
        setComparator();
    }

    private void setComparator(){
        if(type.equals("StartTime")){
            comparator = startTimeComparator;
        }
        else if (type.equals("MaxTime")){
            comparator = maxTimeComparator;
        }
        else if(type.equals("TeamName")){
            comparator = teamNameComparator;
        }
    }

    @Override
    public int compare(Project o1, Project o2) {
        return comparator.compare(o1, o2);
    }


    /**
     * 根据创建时间由小到大排序
     */
    class StartTimeComparator implements Comparator<Project>{

        @Override
        public int compare(Project o1, Project o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }


    /**
     * 根据截止时间由小到大排序
     */
    class MaxTimeComparator implements Comparator<Project>{

        @Override
        public int compare(Project o1, Project o2) {
            return o1.getMaxDate().compareTo(o2.getMaxDate());
        }
    }

    /**
     * 根据团队名称排序
     */
    class TeamNameComparator implements Comparator<Project>{
        @Override
        public int compare(Project o1, Project o2) {
            return Collator.getInstance(Locale.CHINA)
                    .compare(o1.getTeamName(),o2.getTeamName());
        }
    }


}

