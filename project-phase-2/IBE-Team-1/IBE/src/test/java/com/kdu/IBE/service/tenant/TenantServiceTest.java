package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
class TenantServiceTest {
    @Autowired
    ITenantService tenantService;


    public void testForProperties(){
        ResponseEntity<JsonNode> response =tenantService.getTenantProperties("1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JsonNode data = response.getBody();
        assertThat(data).isNotNull();

        ArrayNode properties = (ArrayNode) data.get("data").get("listProperties");
        assertThat(properties).withFailMessage("Properties array should not be null").isNotNull();
        assertThat(properties.size()).withFailMessage("Properties array should have at least one element").isGreaterThan(0);

        for(JsonNode property : properties) {
            assertThat(property.has("property_id")).isTrue();
            assertThat(property.has("property_name")).isTrue();
        }
    }
}