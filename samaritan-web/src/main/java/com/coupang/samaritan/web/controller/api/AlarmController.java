package com.coupang.samaritan.web.controller.api;


import com.coupang.samaritan.web.demo.AlarmStrategy;
import com.coupang.samaritan.web.demo.MonitorScenario;
import com.coupang.samaritan.web.demo.repository.AlarmStrategyRepository;
import com.coupang.samaritan.web.demo.repository.MonitorScenarioRepository;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
@Slf4j
public class AlarmController {

    @Autowired
    AlarmStrategyRepository repository;

    @Autowired
    MonitorScenarioRepository repositoryScenario;

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public @ResponseBody
    Object add(@RequestBody AlarmStrategy strategy) {
        repository.save(strategy);
        return Maps.newHashMap();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.PUT)
    public @ResponseBody Object update(@PathVariable String name, @RequestBody AlarmStrategy strategy) {
        repository.save(strategy);
        return Maps.newHashMap();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public @ResponseBody Object delete(@PathVariable String name) {
        List<MonitorScenario> scenariosList = repositoryScenario.findAll();
        for(int i = 0; i < scenariosList.size(); i++){
            MonitorScenario scenario = scenariosList.get(i);
            if(scenario.getPathMonitorTask().getName().equals(name)){
                return Maps.newHashMap();
            }
        }
        repository.delete(name);
        return Maps.newHashMap();
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody
    List<AlarmStrategy> list() {

        return repository.findAll();
    }
}
