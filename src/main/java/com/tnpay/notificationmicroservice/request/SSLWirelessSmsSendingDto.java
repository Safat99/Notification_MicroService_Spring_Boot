package com.tnpay.notificationmicroservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class SSLWirelessSmsSendingDto {

    private String apiToken;
    private String sid;
    private String phoneNo;
    private String cmsId;
    private Map<String, String> sms;
}
