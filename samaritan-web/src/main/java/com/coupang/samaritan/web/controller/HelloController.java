package com.coupang.samaritan.web.controller;

import com.coupang.samaritan.domain.monitor.Alarm;
import com.coupang.samaritan.domain.monitor.impl.DefaultMessageImpl;
import com.coupang.samaritan.domain.resource.HDFSFile;
import com.coupang.samaritan.domain.resource.MatchEnum;
import com.coupang.samaritan.domain.service.HDFSStatusService;
import com.coupang.samaritan.web.config.WebConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HDFSStatusService hdfsStatusService;

    @RequestMapping(path = {"", "/"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public String home() {
        //alarm.call(Lists.newArrayList(), new DefaultMessageImpl());
        return "Hello!";
    }

    @RequestMapping(path = {"/list"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, HDFSFile> list() {
        String path = "/user/mercury/scm-output/scm_forecast_demand_forecast/@today_yyyyMMdd@/@[national,seoul,daegu]@/output/uplift/part-000@[00~99]@";
        return this.hdfsStatusService.regexMatchListStatus(path);
    }


    @RequestMapping(path = {"/test"}, method = {RequestMethod.GET})
    @ResponseBody
    public List<String> test() {
        Map<String, HDFSFile> allFiles = Maps.newHashMap();
        allFiles.put("part-00001", new HDFSFile());
        allFiles.put("part-00088", new HDFSFile());
        allFiles.put("part-000122", new HDFSFile());
        return MatchEnum.ARRAY_RANGE.findTargets(allFiles, "part-000@[00~11]@");
    }

}
