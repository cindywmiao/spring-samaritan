package com.coupang.samaritan.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.coupang.samaritan"})
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
public class Starter {

    public static void main(String[] args) throws Exception {
        System.setProperty("spring.devtools.restart.enabled", "true");
        System.setProperty("spring.devtools.reload.enabled", "true");
        SpringApplication.run(Starter.class, args);

    }
}
