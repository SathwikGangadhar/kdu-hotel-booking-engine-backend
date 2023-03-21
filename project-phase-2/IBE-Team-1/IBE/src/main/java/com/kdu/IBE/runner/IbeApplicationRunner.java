package com.kdu.IBE.runner;

import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IbeApplicationRunner implements ApplicationRunner {
    @Autowired
    public GraphQlWebClient graphQlCheck;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        graphQlCheck.getCheckGraphQl();
    }
}
