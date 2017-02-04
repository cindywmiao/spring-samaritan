package com.coupang.samaritan.web.controller.api;

import com.coupang.samaritan.domain.task.TaskManager;
import com.coupang.samaritan.web.demo.MonitorScenario;
import com.coupang.samaritan.web.demo.repository.MonitorScenarioRepository;
import com.coupang.samaritan.web.demo.service.MonitorScenarioService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenario")
@Slf4j
public class ScenarioController {

    @Autowired
    MonitorScenarioRepository repository;

    @Autowired
    MonitorScenarioService monitorScenarioService;

    @RequestMapping(path = "/reload", method = RequestMethod.POST)
    public @ResponseBody
    Object reload() {
        monitorScenarioService.deleteAllTasks();
        monitorScenarioService.init();
        return Maps.newHashMap();
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public @ResponseBody
    Object add(@RequestBody MonitorScenario scenario) {
        repository.save(scenario);
        monitorScenarioService.addToTaskManager(scenario);

        return Maps.newHashMap();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public @ResponseBody Object delete(@PathVariable String name) {
        repository.delete(name);
        monitorScenarioService.deleteFromTaskManager(name);
        return Maps.newHashMap();
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody List<MonitorScenario> list() {
        return repository.findAll();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.PUT)
    public @ResponseBody Object update(@PathVariable String name, @RequestBody MonitorScenario scenario) {
        repository.save(scenario);
        return Maps.newHashMap();
    }

}
