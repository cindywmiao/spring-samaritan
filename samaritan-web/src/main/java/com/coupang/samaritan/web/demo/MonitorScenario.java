package com.coupang.samaritan.web.demo;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "monitor_scenario")
public class MonitorScenario {
    @Id
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "success_alarm_strategy")
    private AlarmStrategy successAlarmStrategy;

    @OneToOne
    @JoinColumn(name = "failed_alarm_strategy")
    private AlarmStrategy failedAlarmStrategy;

    @OneToOne
    @JoinColumn(name = "monitor_task")
    private PathMonitorTask pathMonitorTask;


}
