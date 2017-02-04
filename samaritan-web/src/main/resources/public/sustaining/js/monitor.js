/**
 * Created by Cindy.Wang on 1/10/17.
 */

var mainApp = angular.module("mainApp", []);

mainApp.controller('monitorController', function($scope, $http) {

    // var $import = document.querySelector("link[rel='import']").import,
    //     template = $import.querySelector('template').content.cloneNode(true);
    //
    // $(".main-content").empty().append(template);

    // Task
    $scope.showDeleteTask = function(task) {
        $scope.selectedTask = task;
        $('#delete-task-prompt').modal();
    }

    $scope.showUpdateTask = function(task) {
        $scope.selectedTask = task;
        $('#update-task-prompt').modal();
    }

    $scope.showCreateTask = function() {
        $scope.selectedTask = {name: '', pathMatch:'', conditionSymbol:'', conditionValue:'', cron:''};
        $('#create-task-prompt').modal();
    }

    $scope.doDeleteTask = function() {
        $http.delete('/api/task/' + $scope.selectedTask.name).then(
            function(){
                $scope.hdfsPathMonitorTasks.splice($scope.hdfsPathMonitorTasks.indexOf($scope.selectedTask),1);
            }
        );
        $('#delete-task-prompt').modal('hide');
    }

    $scope.doUpdateTask = function() {
        $http.put('/api/task/' + $scope.selectedTask.name, $scope.selectedTask).then(
            // function(){
            //     $scope.hdfsPathMonitorTasks.splice($scope.hdfsPathMonitorTasks.indexOf($scope.selectedTask),1);
            // }
        );
        $('#update-task-prompt').modal('hide');
    }

    $scope.doCreateTask = function() {
        $http.post('/api/task', $scope.selectedTask).then(
            function(){
                $scope.hdfsPathMonitorTasks.push($scope.selectedTask);
            }
        );
        $('#create-task-prompt').modal('hide');
    }

    ///   Alarm
    $scope.showDeleteAlarm = function(alarm) {
        $scope.selectedAlarm = alarm;
        $('#delete-alarm-prompt').modal();
    }

    $scope.showUpdateAlarm = function(alarm) {
        $scope.selectedAlarm = alarm;
        $('#update-alarm-prompt').modal();
    }

    $scope.showCreateAlarm = function() {
        $scope.selectedAlarm = {name: '', method:'', receivers:''};
        $('#create-alarm-prompt').modal();
    }

    $scope.doDeleteAlarm = function() {
        $http.delete('/api/alarm/' + $scope.selectedAlarm.name).then(
            function(){
                $scope.alarmStrategies.splice($scope.alarmStrategies.indexOf($scope.selectedAlarm),1);
            }
        );
        $('#delete-alarm-prompt').modal('hide');
    }

    $scope.doUpdateAlarm = function() {
        $http.put('/api/alarm/' + $scope.selectedAlarm.name, $scope.selectedAlarm).then(
            // function(){
            //     $scope.alarmStrategies.splice($scope.alarmStrategies.indexOf($scope.selectedAlarm),1);
            //     $scope.alarmStrategies.push($scope.selectedAlarm);
            // }
        );
        $('#update-alarm-prompt').modal('hide');
    }

    $scope.doCreateAlarm = function() {
        $http.post('/api/alarm', $scope.selectedAlarm).then(
            function(){
                $scope.alarmStrategies.push($scope.selectedAlarm);
            }
        );
        $('#create-alarm-prompt').modal('hide');
    }

    // Scenario
    $scope.showDeleteScenario = function(scenario) {
        $scope.selectedScenario = scenario;
        $('#delete-scenario-prompt').modal();
    }

    $scope.showCreateScenario = function() {
        $scope.selectedScenario = {name: '', pathMonitorTask:{}, successAlarmStrategy:{}, failedAlarmStrategy:{}};
        $('#create-scenario-prompt').modal();
    }

    $scope.showUpdateScenario = function(scenario){
        $scope.selectedScenario = scenario;
        $('#update-scenario-prompt').modal();
    }

    $scope.doDeleteScenario = function() {
        $http.delete('/api/scenario/' + $scope.selectedScenario.name).then(
            function(){
                $scope.scenarios.splice($scope.scenarios.indexOf($scope.selectedScenario),1);
            }
        );
        $('#delete-scenario-prompt').modal('hide');
        $scope.showMessage("the scenario is removed");
    }

    $scope.doCreateScenario = function() {
        console.log($scope.selectedScenario);
        $http.post('/api/scenario', $scope.selectedScenario).then(
            function(){
                $scope.scenarios.push($scope.selectedScenario);
            }
        );

        $('#create-scenario-prompt').modal('hide');
        $scope.showMessage("a new scenario is running");
    }

    $scope.doUpdateScenario = function() {
        $http.put('/api/scenario/' + $scope.selectedScenario.name, $scope.selectedScenario).then(
            // function(){
            //     $scope.alarmStrategies.splice($scope.alarmStrategies.indexOf($scope.selectedAlarm),1);
            //     $scope.alarmStrategies.push($scope.selectedAlarm);
            // }
        );
        $('#update-scenario-prompt').modal('hide');
        $scope.showMessage("this scenario is running");
    }


    // Message
    $scope.showMessage = function(msg) {
        $('#alert-bar-msg').empty().append(msg);
        $('#alert-bar').fadeTo(1000, 1, function() {
            $('#alert-bar').delay(2000).fadeTo(2000, 0,function(){
                $('#alert-bar').hide()
            });
        });
    };

    // reload
    $scope.reload = function() {
        $http.post('/api/scenario/reload').then(
            function(response){
                $scope.showMessage("scenarios are reloaded");
            }
        );
        $scope.init();
    }

    // init
    $scope.init = function () {
        $http.get('/api/task').then(
            function(response){
                $scope.hdfsPathMonitorTasks = response.data;
            }
        );
        $http.get('/api/alarm').then(
            function(response){
                $scope.alarmStrategies = response.data;
            }
        );
        $http.get('/api/scenario').then(
            function(response){
                $scope.scenarios = response.data;
                console.log($scope.scenarios);
            }
        );

    }

    $scope.init();
});