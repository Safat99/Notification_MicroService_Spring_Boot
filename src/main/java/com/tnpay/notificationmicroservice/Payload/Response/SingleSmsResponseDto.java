package com.tnpay.notificationmicroservice.Payload.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class SingleSmsResponseDto {

    private String status;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("smsinfo")
    private ArrayList<SmsInfo> smsInfo;
}

class SmsInfo{

    @JsonProperty("sms_status")
    private String smsStatus;
    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("sms_type")
    private String smsType;

    @JsonProperty("sms_body")
    private String smsBody;

    @JsonProperty("csms_id")
    private String csmsId;

    @JsonProperty("reference_id")
    private String referenceId;
}


