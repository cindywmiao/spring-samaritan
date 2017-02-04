package com.coupang.samaritan.web.demo.repository;


import com.coupang.samaritan.web.demo.PathMonitorTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathMonitorTaskRepository extends JpaRepository<PathMonitorTask, String> {

}
