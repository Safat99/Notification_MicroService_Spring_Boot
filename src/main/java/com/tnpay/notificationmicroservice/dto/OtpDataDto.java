package com.tnpay.notificationmicroservice.dto;

import lombok.Getter;

@Getter
public class OtpDataDto {

    private final String otp;

    private final long timestamp;

    public OtpDataDto(String otp, long timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

}

