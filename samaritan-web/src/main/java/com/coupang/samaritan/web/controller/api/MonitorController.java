package com.coupang.samaritan.web.controller.api;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public Object listMonitorTasks() {

        return Lists.newArrayList("testA", "testB");
    }



}
