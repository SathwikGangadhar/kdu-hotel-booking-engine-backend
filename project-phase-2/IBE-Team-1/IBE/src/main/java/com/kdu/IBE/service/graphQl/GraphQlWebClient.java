package com.kdu.IBE.service.graphQl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdu.IBE.service.secretCredentials.SecretCredentialsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import software.amazon.awssdk.services.pinpointemail.model.Body;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 */
@Data
@Service
public class GraphQlWebClient {
    public  WebClient.RequestBodySpec requestBodySpec ;
    @Autowired
    private SecretCredentialsService secretCredentialsService;

    public JsonNode getGraphQlResponse(Map<String, Object> requestBody){
        WebClient.ResponseSpec response = this.requestBodySpec
                .body(BodyInserters.fromValue(requestBody))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        String bodyString = response.bodyToMono(String.class).block();
        Body body;
        JsonNode jsonNode;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNode = objectMapper.readTree(bodyString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
    }
    @PostConstruct
    private void setRequestBodySpec(){
        this.requestBodySpec = WebClient
                .builder()
                .baseUrl(secretCredentialsService.getSecretCredentialsModel().getGraphqlUrl())
                .defaultHeader("x-api-key", secretCredentialsService.getSecretCredentialsModel().getGraphqlApiKey())
                .build()
                .method(HttpMethod.POST)
                .uri("/graphql");
    }
}
