package com.coupang.samaritan.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DomainConfig {

    /**
     * util class, to send sync http request
     * */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
