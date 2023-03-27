package com.kdu.IBE.service.date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IDateService {
    ResponseEntity<Map<String, Integer>> getMinDate();
}
