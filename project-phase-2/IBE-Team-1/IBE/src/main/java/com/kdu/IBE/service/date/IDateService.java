package com.kdu.IBE.service.date;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IDateService {
    ResponseEntity<Map<String, Integer>> getMinDate();
}
