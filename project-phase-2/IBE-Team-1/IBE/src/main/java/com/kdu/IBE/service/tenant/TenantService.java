package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.services.pinpointemail.model.Body;

import java.nio.charset.StandardCharsets;
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
        WebClient.ResponseSpec response = graphQlWebClient.requestBodySpec
                .body(BodyInserters.fromValue(requestBodyPropertyCount))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        String bodyString = response.bodyToMono(String.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(bodyString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        take = jsonNode.get("data").get("countProperties").asInt();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", "query MyQuery {\n" +
                "  listProperties(where: {tenant_id: {equals: " + tenantId + "}}, skip: " + Integer.toString(skip) + ", take: " + Integer.toString(take) + ") {\n" +
                "    property_id\n" +
                "    property_name\n" +
                "  }\n" +
                "}"
        );

        WebClient.ResponseSpec responseProperties = graphQlWebClient.requestBodySpec
                .body(BodyInserters.fromValue(requestBody))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        bodyString = responseProperties.bodyToMono(String.class).block();

        Body body;
        try {
            jsonNode = objectMapper.readTree(bodyString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<JsonNode>(jsonNode, HttpStatus.OK);
    }
}
