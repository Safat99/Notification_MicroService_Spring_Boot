package com.tnpay.notificationmicroservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Getter
@Configuration
@PropertySource("classpath:sms.properties")
public class SmsSenderConfiguration {

    @Value("${sms.api-url}")
    private String apiUrl;

    @Value("${sms.api-token}")
    private String apiToken;

    @Value("${sms.sid}")
    private String sId;

    @Value("${sms.cms-id}")
    private String cmsId;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultCookie("Key", "Value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", apiUrl))
                .build();
    }

}
