package com.coupang.samaritan.web.demo.repository;


import com.coupang.samaritan.web.demo.AlarmStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlarmStrategyRepository extends JpaRepository<AlarmStrategy, String> {

}
