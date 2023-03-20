package com.kdu.IBE.service;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HealthService implements HealthIndicator{
    public static final String API_SERVICE="API SERVICE";
    public boolean isHealthGood(){
        return true;
    }
    @Override
    public Health health() {
        if(isHealthGood()){
           return Health.up().withDetail(API_SERVICE,"API is running").build();
       }
       else {
           return Health.down().withDetail(API_SERVICE,"API is not running").build();
       }
    }
}
