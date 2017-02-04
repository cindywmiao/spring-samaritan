package com.coupang.samaritan.web.demo.repository;


import com.coupang.samaritan.web.demo.MonitorScenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorScenarioRepository extends JpaRepository<MonitorScenario, String> {

}
