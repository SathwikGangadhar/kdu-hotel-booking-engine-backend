package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.kdu.IBE.model.returnDto.PropertyReturnModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
class TenantServiceTest {
    @Autowired
    ITenantService tenantService;


    public void testForProperties(){
        ResponseEntity<List<PropertyReturnModel>> response =tenantService.getTenantProperties("1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}