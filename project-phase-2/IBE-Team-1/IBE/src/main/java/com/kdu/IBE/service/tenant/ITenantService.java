package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ITenantService {
    public ResponseEntity<JsonNode> getTenantProperties(String tenantId);
}
