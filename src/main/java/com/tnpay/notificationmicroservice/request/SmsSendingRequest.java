package com.tnpay.notificationmicroservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SmsSendingRequest {

    @JsonProperty("phone_number")
    private String phoneNumber;

    @Size(max = 1000, message = "{validation.name.size.too_long}")
    private String message;
}
