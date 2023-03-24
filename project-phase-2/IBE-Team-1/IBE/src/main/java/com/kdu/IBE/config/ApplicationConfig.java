package com.kdu.IBE.config;

import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.service.secretCredentials.SecretCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
public class ApplicationConfig {
    @Autowired
    public SecretCredentialsService secretCredentialsService;
    @Autowired
    public GraphQlWebClient graphQlWebClient;

    @Bean
    public DataSource dataSource() {

        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://" + secretCredentialsService.getSecretCredentialsModel().getHost() + "/" + secretCredentialsService.getSecretCredentialsModel().getUsername())
                .username(secretCredentialsService.getSecretCredentialsModel().getUsername())
                .password(secretCredentialsService.getSecretCredentialsModel().getPassword())
                .build();
    }
}
