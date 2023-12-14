package com.tnpay.notificationmicroservice.utils;

import com.tnpay.notificationmicroservice.Payload.Response.SingleSmsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "sms-service", url = "${sms.api-url}")
public interface SmsFeignClient {

    /**
     * Sends an SMS using the specified parameters
     *
     * @param apiToken The API token for authentication
     * @param sid   The SID (Service Identifier) for the SMS service.
     * @param phoneNo  The relevant phone number
     * @param cmsId The CSMS ID (Custom SMS ID) for tracking purposes
     * @param sms The map containing the text message
     * @return A full result of the SMS will be returned
     */
    @PostMapping("/send-sms")
    ResponseEntity<SingleSmsResponseDto> sendSms(@RequestParam("api_token") String apiToken,
                                                 @RequestParam String sid,
                                                 @RequestParam("msisdn") String phoneNo,
                                                 @RequestParam("csms_id") String cmsId,
                                                 @RequestBody Map<String, String> sms
    );
}
