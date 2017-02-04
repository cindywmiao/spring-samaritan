package com.coupang.samaritan.domain.task.impl;

import com.coupang.samaritan.domain.task.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class TaskManagerImpl implements TaskManager {

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    Map<String, ScheduledFuture> taskMap = new HashMap<>();

    @PostConstruct
    public void init() {
        scheduler.afterPropertiesSet();
        scheduler.setPoolSize(10);
        // load all persisted task into memory

    }

    // job Name should be unique, sync the method, usually, this won't be a frequently used method
    public synchronized void addCronJobTask(String taskName, Runnable runnable, String cron) {
        if (null != taskMap.get(taskName)) {
            throw new RuntimeException("taskName:" + taskName + " exists");
        } else {
            ScheduledFuture future = scheduler.schedule(runnable, new CronTrigger(cron));
            taskMap.put(taskName, future);
            log.info("task Map size: {}", taskMap.size());
        }
    }

    // the thread should be dead, and memory should be gc
    public synchronized void removeCronJobTask(String taskName) {
        ScheduledFuture future = taskMap.get(taskName);
        if(future != null) {
            future.cancel(false);
            taskMap.remove(taskName);
        } else {
            log.info("{} is not exists", taskName);
        }
        log.info("task Map size: {}", taskMap.size());
    }

    public synchronized void removeAll() {
        taskMap.entrySet().forEach(entry -> entry.getValue().cancel(false));
        taskMap.clear();
    }
}
