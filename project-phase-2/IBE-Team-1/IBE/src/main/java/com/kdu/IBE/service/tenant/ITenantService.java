package com.kdu.IBE.service.tenant;

import com.kdu.IBE.model.responseDto.PropertyReturnModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITenantService {
     ResponseEntity<List<PropertyReturnModel>> getTenantProperties(String tenantId);
}
