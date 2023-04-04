package com.kdu.IBE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
//import org.springframework.mai



@Configuration
public class SesConfig {

    @Value("${region}")
    private String region;




}
