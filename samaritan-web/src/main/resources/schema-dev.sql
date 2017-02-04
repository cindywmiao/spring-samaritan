DROP TABLE path_monitor_task IF EXISTS;
DROP TABLE alarm_strategy IF EXISTS;
DROP TABLE monitor_scenario IF EXISTS;

CREATE TABLE path_monitor_task (
  name        VARCHAR(50) PRIMARY KEY,
  path_match  VARCHAR(255) NOT NULL,
  condition_symbol VARCHAR(5) NOT NULL,
  condition_value INT,
  cron        VARCHAR(255) NOT NULL
);

CREATE TABLE alarm_strategy (
  name        VARCHAR(50) PRIMARY KEY,
  method      VARCHAR(10) NOT NULL,
  receivers   VARCHAR(255) NOT NULL
);

CREATE TABLE monitor_scenario (
  name            VARCHAR(50) PRIMARY KEY,
  monitor_task    VARCHAR(50) NOT NULL,
  success_alarm_strategy  VARCHAR(50) NOT NULL,
  failed_alarm_strategy  VARCHAR(50) NOT NULL,
);

--- pre-load data for test
INSERT INTO path_monitor_task values ('t-forecast1','/user/mercury/scm-output/scm_forecast_demand_forecast/@today_yyyyMMdd@/@[national,seoul,daegu]@/output/uplift/part-000@[00~99]@','>',0,'1 5 5 * * *');
INSERT INTO path_monitor_task values ('t-forecast2','/user/mercury/scm-output/scm_forecast_demand_forecast/@today_yyyyMMdd@/result-21-days-@[national,daegu,seoul]@.csv','>',100,'1 10 5 * * *');
INSERT INTO path_monitor_task values ('t-tip-roq1','/user/mercury/scm-output/tip2/@latest_dir@/roq_result_base.csv','>',100,'0 30 7 * * *');
INSERT INTO alarm_strategy values('yang', 'PHONE', '13795428319');
INSERT INTO alarm_strategy values('sys', 'LOG', 'sys');
INSERT INTO alarm_strategy values('alex', 'PHONE', '13918344962');
INSERT INTO monitor_scenario values('scenario1', 't-forecast1', 'sys', 'sys');
INSERT INTO monitor_scenario values('scenario2', 't-forecast2', 'sys', 'sys');
INSERT INTO monitor_scenario values('scenario3', 't-tip-roq1', 'sys', 'sys');