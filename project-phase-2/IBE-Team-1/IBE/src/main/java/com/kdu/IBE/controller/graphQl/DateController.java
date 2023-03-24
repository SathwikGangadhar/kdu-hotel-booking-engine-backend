package com.kdu.IBE.controller.graphQl;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.service.date.IDateService;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping(EndPointConstants.DATE_REQUEST_MAPPING)
public class DateController {
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    @Autowired
    public IDateService dateService;

    @GetMapping(EndPointConstants.GET_MIN_DATE_ALL)
    ResponseEntity<Map<String, Integer>> getMinDateAll() {
        return dateService.getMinDate();
    }
}
