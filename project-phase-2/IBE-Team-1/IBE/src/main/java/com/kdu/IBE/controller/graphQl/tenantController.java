package com.kdu.IBE.controller.graphQl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.service.tenant.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(EndPointConstants.TENANT_REQUEST_MAPPING)
public class tenantController {
    @Autowired
    ITenantService tenantService;
    @GetMapping(EndPointConstants.GET_TENANT_PROPERTIES)
    ResponseEntity<JsonNode> getTenantProperties(@RequestParam(name = "tenant_id") String tenantId) {
        return tenantService.getTenantProperties(tenantId);
    }
}
