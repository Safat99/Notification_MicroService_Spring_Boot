package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.SingleSmsResponseDto;
import com.tnpay.notificationmicroservice.request.SmsSendingRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface SmsService {
    ResponseEntity<SingleSmsResponseDto> sendSms(SmsSendingRequest smsSendingDto);
    Mono<SingleSmsResponseDto> sendSmsUsingWebClient(SmsSendingRequest smsSendingDto);

}
