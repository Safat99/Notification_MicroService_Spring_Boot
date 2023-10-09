package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.request.ForgetPasswordRequest;
import com.tnpay.notificationmicroservice.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }


    /**
     * this will send the email an otp which will be invalid after 5 minutes
     * @param forgetPasswordRequest
     */
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        return otpService.forgetPassword(forgetPasswordRequest);
    }
}
