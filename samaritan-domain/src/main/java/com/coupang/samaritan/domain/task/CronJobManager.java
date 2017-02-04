package com.coupang.samaritan.domain.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
//@Configuration
//@Component
public class CronJobManager implements SchedulingConfigurer {

//    private String cronConfig() {
//        String cronTabExpression = "*/5 * * * * *";
//        return cronTabExpression;
//    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(() -> {log.info("task is running");}, "10 * * * * *");

//        taskRegistrar.addTriggerTask(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, new Trigger() {
//            @Override
//            public Date nextExecutionTime(TriggerContext triggerContext) {
//                String cron = cronConfig();
//                log.info(cron);
//                CronTrigger trigger = new CronTrigger(cron);
//                Date nextExec = trigger.nextExecutionTime(triggerContext);
//                return nextExec;
//            }
//        });
    }

    public void updateCronTask() {

    }

    public void removeCronTask() {

    }
}
