package com.kdu.IBE.controller;

import com.kdu.IBE.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @Autowired private HealthService healthService;
    @GetMapping
    public ResponseEntity<Health> healthCheck(){
        Health health = healthService.health();
        HttpStatus status = HttpStatus.OK;
        if (health.getStatus().equals(Status.DOWN)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(health, status);
    }
}