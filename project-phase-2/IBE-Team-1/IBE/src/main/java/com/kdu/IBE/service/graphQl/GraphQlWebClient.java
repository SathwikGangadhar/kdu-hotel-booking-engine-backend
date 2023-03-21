package com.kdu.IBE.service.graphQl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class GraphQlWebClient {

    @Value("${graphql.url}")
    private String graphQlUrl;

    @Value("${graphql.apiKey}")
    private String graphQlApiKey;


    public  WebClient.RequestBodySpec requestBodySpec = WebClient
            .builder()
            .baseUrl("https://hxsyj7f2obc2tk45fdyu5pw7su.appsync-api.ap-south-1.amazonaws.com")
            .defaultHeader("x-api-key", "da2-mhnlrcroofhmpdex6ov7or4qqi")
            .build()
            .method(HttpMethod.POST)
            .uri("/graphql");

 public void getCheckGraphQl(){
     Map<String, Object> requestBody = new HashMap<>();
     requestBody.put("query","query MyQuery { "
             + "countBookingStatuses "
             + "countTenants "
             + "}");

     WebClient.ResponseSpec response = requestBodySpec
             .body(BodyInserters.fromValue(requestBody))
             .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
             .acceptCharset(StandardCharsets.UTF_8)
             .retrieve();
     String bodyString = response.bodyToMono(String.class).block();
 }
}
