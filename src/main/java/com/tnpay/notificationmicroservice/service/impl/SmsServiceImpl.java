package com.tnpay.notificationmicroservice.service.impl;

import com.tnpay.notificationmicroservice.Payload.Response.SingleSmsResponseDto;
import com.tnpay.notificationmicroservice.config.SmsSenderConfiguration;
import com.tnpay.notificationmicroservice.request.SSLWirelessSmsSendingDto;
import com.tnpay.notificationmicroservice.request.SmsSendingRequest;
import com.tnpay.notificationmicroservice.service.SmsService;
import com.tnpay.notificationmicroservice.utils.SmsFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    private final SmsSenderConfiguration smsSenderConfiguration;
    private final SmsFeignClient smsFeignClient;
    private final WebClient webClient;

    Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);


    @Autowired
    public SmsServiceImpl(SmsSenderConfiguration smsSenderConfiguration, SmsFeignClient smsFeignClient, WebClient webClient) {
        this.smsSenderConfiguration = smsSenderConfiguration;
        this.smsFeignClient = smsFeignClient;
        this.webClient = webClient;
    }

    public ResponseEntity<SingleSmsResponseDto> sendSms(SmsSendingRequest smsSendingDto) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sms", smsSendingDto.getMessage());

        logger.info("Feign Client API has been invoked!!!");


        SSLWirelessSmsSendingDto request = SSLWirelessSmsSendingDto.builder()
                .apiToken(smsSenderConfiguration.getApiToken())
                .sid(smsSenderConfiguration.getSId())
                .phoneNo(smsSendingDto.getPhoneNumber())
                .cmsId(smsSenderConfiguration.getCmsId())
                .sms(requestBody)
                .build();

        return smsFeignClient.sendSms(
                request.getApiToken(),
                request.getSid(),
                request.getPhoneNo(),
                request.getCmsId(),
                request.getSms()
        );
    }

    public Mono<SingleSmsResponseDto> sendSmsUsingWebClient(SmsSendingRequest smsSendingDto) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sms", smsSendingDto.getMessage());


        Mono<SingleSmsResponseDto> mono = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/send-sms")
                        .queryParam("api_token", smsSenderConfiguration.getApiToken())
                        .queryParam("sid", smsSenderConfiguration.getSId())
                        .queryParam("msisdn", smsSendingDto.getPhoneNumber())
                        .queryParam("csms_id", smsSenderConfiguration.getCmsId())
                        .build())
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(SingleSmsResponseDto.class);

        logger.info("WebClient API has been invoked!!!");
        return mono;
    }
}