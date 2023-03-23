package com.kdu.IBE.service.secretCredentials;

import com.google.gson.Gson;
import com.kdu.IBE.model.SecretCredentialsModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.regions.Region;

import javax.annotation.PostConstruct;

@Data
@Component
public class SecretCredentialsService {
    private SecretCredentialsModel secretCredentialsModel;
    private Gson gson=new Gson();
    @Value("${aws.profile}")
    private String awsProfileName;
    public SecretCredentialsModel getSecretCredentials() {

        String secretName = "team-01-graphQlCredentials";
        Region region = Region.of("ap-south-1");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create(awsProfileName))
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secret = getSecretValueResponse.secretString();

        SecretCredentialsModel secretCredentials=gson.fromJson(secret, SecretCredentialsModel.class);
        return secretCredentials;
    }

    @PostConstruct
    public void setSecretCredentialsModel(){
        this.secretCredentialsModel = getSecretCredentials();
    }
}
