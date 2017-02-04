package com.coupang.samaritan.web.demo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "path_monitor_task")
public class PathMonitorTask {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "path_match")
    private String pathMatch;

    @Column(name = "condition_symbol")
    private String conditionSymbol;

    @Column(name = "conditionValue")
    private Integer conditionValue;

    @Column(name = "cron")
    private String cron;
}
