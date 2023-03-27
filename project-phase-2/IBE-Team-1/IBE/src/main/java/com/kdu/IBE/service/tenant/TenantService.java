package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TenantService implements ITenantService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    public ResponseEntity<JsonNode> getTenantProperties(String tenantId) {
        Map<String, Integer> minRatesByDate = new HashMap<>();
        int skip = 0;
        int take = 0;

        Map<String, Object> requestBodyPropertyCount = new HashMap<>();
        requestBodyPropertyCount.put("query", "query MyQuery {\n" +
                "  countProperties\n" +
                "}"
        );
        JsonNode jsonNode=graphQlWebClient.getGraphQlResponse(requestBodyPropertyCount);
        take = jsonNode.get("data").get("countProperties").asInt();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", "query MyQuery {\n" +
                "  listProperties(where: {tenant_id: {equals: " + tenantId + "}}, skip: " + Integer.toString(skip) + ", take: " + Integer.toString(take) + ") {\n" +
                "    property_id\n" +
                "    property_name\n" +
                "  }\n" +
                "}"
        );

       jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
        return new ResponseEntity<JsonNode>(jsonNode, HttpStatus.OK);
    }
}
