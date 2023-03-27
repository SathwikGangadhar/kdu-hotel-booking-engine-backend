package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.returnDto.PropertyReturnModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITenantService {
     ResponseEntity<List<PropertyReturnModel>> getTenantProperties(String tenantId);
}
