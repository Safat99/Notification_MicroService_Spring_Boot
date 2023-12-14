package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.Payload.Response.SingleSmsResponseDto;
import com.tnpay.notificationmicroservice.request.SmsSendingRequest;
import com.tnpay.notificationmicroservice.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notification/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send-with-feign-client")
    public ResponseEntity<SingleSmsResponseDto> sendMessage(@Valid @RequestBody SmsSendingRequest smsSendingDto) {
        return smsService.sendSms(smsSendingDto);
    }

    @PostMapping("/send-with-webClient")
    public ResponseEntity<Mono<SingleSmsResponseDto>> sendMessageUsingWebClient(@Valid @RequestBody SmsSendingRequest smsSendingDto) {
        return ResponseEntity.ok(smsService.sendSmsUsingWebClient(smsSendingDto));
    }

}
