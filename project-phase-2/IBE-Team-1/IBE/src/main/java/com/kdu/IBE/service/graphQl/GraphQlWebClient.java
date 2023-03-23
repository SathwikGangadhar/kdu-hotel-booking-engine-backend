package com.kdu.IBE.service.graphQl;

import com.kdu.IBE.service.secretCredentials.SecretCredentialsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

/**
 *
 */
@Data
@Service
public class GraphQlWebClient {
    public  WebClient.RequestBodySpec requestBodySpec ;
    @Autowired
    private SecretCredentialsService secretCredentialsService;
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
