package com.coupang.samaritan.domain.monitor;


public interface Item <T> {

    ItemType getItemType();

    String getItemId();

    MonitorStrategy getMonitorStrategy();

    T getMonitorTarget();

    Alarm getAlarm();



    enum ItemType {
        HDFS_FILE
    }
}
