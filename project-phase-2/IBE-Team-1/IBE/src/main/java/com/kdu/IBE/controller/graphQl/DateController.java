package com.kdu.IBE.controller.graphQl;

import com.kdu.IBE.service.date.IDateService;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/date")
public class DateController {

    @Autowired
    public GraphQlWebClient graphQlWebClient;

    @Autowired
    public IDateService dateService;

    @GetMapping("/get/min/date/all")
    ResponseEntity<Map<String, Integer>> getMinDateAll(){
        return dateService.getMinDate();
    }


}
