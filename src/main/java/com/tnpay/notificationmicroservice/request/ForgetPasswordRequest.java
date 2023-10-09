package com.tnpay.notificationmicroservice.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ForgetPasswordRequest {

    @Email
    @NotBlank
    private String email;
}
