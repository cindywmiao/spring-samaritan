package com.coupang.samaritan.web.controller.api;

import com.coupang.samaritan.web.demo.MonitorScenario;
import com.coupang.samaritan.web.demo.PathMonitorTask;
import com.coupang.samaritan.domain.task.TaskManager;
import com.coupang.samaritan.web.demo.repository.MonitorScenarioRepository;
import com.coupang.samaritan.web.demo.repository.PathMonitorTaskRepository;
import com.coupang.samaritan.web.demo.service.MonitorScenarioService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Slf4j
public class TaskController {

    @Autowired
    TaskManager taskManager;

    @Autowired
    PathMonitorTaskRepository repository;

    @Autowired
    MonitorScenarioRepository repositoryScenario;


//    Random random = new Random();
//
//    // test method
//    @RequestMapping(path = "/add", method = RequestMethod.GET)
//    public void add() {
//        Integer n = Math.abs(random.nextInt() % 10);
//        String taskName = Integer.toString(n);
//        System.out.println(taskName);
//        taskManager.addCronJobTask(taskName, () -> log.info("taskName: {}", taskName), taskName + " * * * * *");
//    }
//
//    @RequestMapping(path = "/cancel/{taskName}", method = RequestMethod.GET)
//    public void cancel(@PathVariable String taskName) {
//        taskManager.removeCronJobTask(taskName);
//    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.POST)
    public @ResponseBody Object add(@RequestBody PathMonitorTask task) {
        repository.save(task);
        return Maps.newHashMap();
    }

    @RequestMapping(path = {"", "/{name}"}, method = RequestMethod.PUT)
    public @ResponseBody Object update(@PathVariable String name, @RequestBody PathMonitorTask task) {
        repository.save(task);
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

//    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
//    public @ResponseBody List<MonitorScenario> list() {
//        return repositoryScenario.findAll();
//    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody List<PathMonitorTask> list() {

        return repository.findAll();
    }

}
