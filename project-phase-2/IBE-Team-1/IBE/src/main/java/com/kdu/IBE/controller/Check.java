package com.kdu.IBE.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check")
public class Check {
    @GetMapping("/getCheck")
    ResponseEntity<String> getCheck(){
        return new ResponseEntity<String>("Team01", HttpStatus.OK);
    }
}
