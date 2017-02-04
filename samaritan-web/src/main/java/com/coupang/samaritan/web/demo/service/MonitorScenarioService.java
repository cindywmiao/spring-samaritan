package com.coupang.samaritan.web.demo.service;

import com.coupang.samaritan.domain.service.HDFSStatusService;
import com.coupang.samaritan.domain.task.TaskManager;
import com.coupang.samaritan.web.demo.AlarmStrategy;
import com.coupang.samaritan.web.demo.MonitorScenario;
import com.coupang.samaritan.web.demo.repository.MonitorScenarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MonitorScenarioService {

    @Autowired
    MonitorScenarioRepository repo;

    @Autowired
    CallService callService;

    @Autowired
    TaskManager taskManager;

    @Autowired
    HDFSStatusService hdfsStatusService;

    // load existed scenario into taskManager
    @PostConstruct
    public void init() {
        List<MonitorScenario> scenarios = repo.findAll();
        scenarios.forEach(scenario -> addToTaskManager(scenario));
    }

    public void addToTaskManager(MonitorScenario scenario) {
        log.info("scenario: {} is loaded ", scenario);
        taskManager.addCronJobTask(scenario.getName(),
                new MonitorScenarioRunnable(scenario) ,scenario.getPathMonitorTask().getCron());
    }

    public void deleteFromTaskManager(String scenarioName) {
        taskManager.removeCronJobTask(scenarioName);
    }

    public void deleteAllTasks() {
        taskManager.removeAll();
    }

    @AllArgsConstructor
    class MonitorScenarioRunnable implements Runnable {

        private MonitorScenario monitorScenario;

        @Override
        public void run() {
            String pathMatch = monitorScenario.getPathMonitorTask().getPathMatch();
            String conditionSymbol = monitorScenario.getPathMonitorTask().getConditionSymbol();
            Integer conditionValue = monitorScenario.getPathMonitorTask().getConditionValue();


            log.info("pathMatch: {}, conditionSymbol: {}, conditionValue: {}", pathMatch, conditionSymbol, conditionValue);
            if (hdfsStatusService.checkPathMatch(pathMatch, conditionSymbol, conditionValue)) {
                log.info("scenario: {} is success", monitorScenario);
                handleAlarm(monitorScenario.getSuccessAlarmStrategy());
            } else {
                log.info("scenario: {} is failed", monitorScenario);
                handleAlarm(monitorScenario.getFailedAlarmStrategy());
            }
        }

        private void handleAlarm(AlarmStrategy alarmStrategy) {
            switch (alarmStrategy.getMethod()) {
                case PHONE:
                    phone(Arrays.asList(alarmStrategy.getReceivers().split(",")));
                    break;
                case LOG:
                    log.info("alarm info: {}", alarmStrategy);
                    break;
                default:
                    throw new RuntimeException("unsupported alarm method");
            }
        }


        private void phone(List<String> receivers) {
            log.info("phone receivers: {}", receivers);
            receivers.forEach(receiver -> callService.call(receiver));
        }
    }

}
