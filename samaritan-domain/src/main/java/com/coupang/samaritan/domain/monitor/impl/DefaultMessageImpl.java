package com.coupang.samaritan.domain.monitor.impl;


import com.coupang.samaritan.domain.monitor.Message;
import lombok.Data;

@Data
public class DefaultMessageImpl implements Message {

    private String content;

}
