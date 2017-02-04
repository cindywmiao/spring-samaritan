package com.coupang.samaritan.domain.monitor.impl;


import com.coupang.samaritan.domain.monitor.Alarm;
import com.coupang.samaritan.domain.monitor.Message;
import com.coupang.samaritan.domain.monitor.Receiver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DemoAlarm implements Alarm {

//    public void call(List<String> receiverList, Message message) {
//        receiverList.forEach(receiver -> log.warn("receiver: " + receiver + " message: " + message.getContent()));
//    }

    Receiver receiver;

    Message message;

    @Override
    public void alarm() {
        List<String> receiverList =  (List<String>)receiver;
        receiverList.forEach(receiver -> log.warn("receiver: " + receiver + " message: " + message.getContent()));
    }
}
