package com.coupang.samaritan.domain.task;

import org.springframework.stereotype.Component;


@Component
public interface TaskManager {

    /**
     * ThreadSafe
     * */
    void addCronJobTask(String taskName, Runnable runnable, String cron);

    /**
     * ThreadSafe
     * */
    void removeCronJobTask(String taskName);

    void removeAll();
}
