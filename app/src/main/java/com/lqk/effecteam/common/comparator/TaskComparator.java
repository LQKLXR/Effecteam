package com.lqk.effecteam.common.comparator;

import com.lqk.effecteam.common.entity.Task;

import java.util.Comparator;

/**
 * Create By LiuQK on 2021/4/20
 * Describe: 任务的排序器
 */
public class TaskComparator implements Comparator<Task> {

    private String type;
    private Comparator<Task> comparator;

    private PriorityComparator priorityComparator = new PriorityComparator();
    private StartTimeComparator startTimeComparator = new StartTimeComparator();
    private MaxTimeComparator maxTimeComparator = new MaxTimeComparator();

    public TaskComparator(String type) {
        this.type = type;
        setComparator();
    }

    public void setType(String type) {
        this.type = type;
        setComparator();
    }

    public void setComparator() {
        if (type.equals("Priority")) {
            comparator = priorityComparator;
        } else if (type.equals("StartTime")) {
            comparator = startTimeComparator;
        } else if (type.equals("MaxTime")) {
            comparator = maxTimeComparator;
        }
    }

    @Override
    public int compare(Task o1, Task o2) {
        return comparator.compare(o1, o2);
    }

    /**
     * 根据优先级由大到小排序
     */
    class PriorityComparator implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            if (o2.getPriority() > o1.getPriority()) {
                return 1;
            } else if (o2.getPriority() == o1.getPriority()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


    /**
     * 根据创建时间由小到大排序
     */
    class StartTimeComparator implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }


    /**
     * 根据截止时间由小到大排序
     */
    class MaxTimeComparator implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            return o1.getMaxDate().compareTo(o2.getMaxDate());
        }
    }
}
