package com.coupang.samaritan.domain.monitor.impl;

import com.coupang.samaritan.domain.monitor.Alarm;
import com.coupang.samaritan.domain.monitor.MonitorStrategy;
import com.coupang.samaritan.domain.monitor.MonitorTask;

public class HDFSFileMonitorTask implements MonitorTask {

    // task meta info
    private HDFSFileItem hdfsFileItem;

    @Override
    public void run() {
        // currently, just cron info
        MonitorStrategy strategy = hdfsFileItem.getMonitorStrategy();
        // currently it's the HDFS files
        Object target = hdfsFileItem.getMonitorTarget();
        Alarm alarm = hdfsFileItem.getAlarm();
        // need receiver

    }
}
