package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.request.ForgetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface OtpService {
    String generateRandomOtp(int length);

    boolean validateOtp(String email, String otp);

    ResponseEntity<?> sendOtp(String email, String otp);

    ResponseEntity<?> forgetPassword(ForgetPasswordRequest forgetPasswordRequest);
}
