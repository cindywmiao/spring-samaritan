package com.coupang.samaritan.domain.monitor.impl;

import com.coupang.samaritan.domain.monitor.Alarm;
import com.coupang.samaritan.domain.monitor.Item;
import com.coupang.samaritan.domain.monitor.MonitorStrategy;
import lombok.Data;


@Data
public class HDFSFileItem implements Item {

    private String itemId;

    private ItemType itemType = ItemType.HDFS_FILE;

    private MonitorStrategy monitorStrategy;

    private Object monitorTarget;

    private Alarm alarm;

//    @Override
//    public ItemType getItemType() {
//        return ItemType.HDFS_FILE;
//    }
//
//    @Override
//    public String getItemId() {
//        return itemId;
//    }
}
