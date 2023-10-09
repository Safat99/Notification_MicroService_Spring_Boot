package com.tnpay.notificationmicroservice.service.impl;

import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.dto.OtpDataDto;
import com.tnpay.notificationmicroservice.request.ForgetPasswordRequest;
import com.tnpay.notificationmicroservice.service.EmailService;
import com.tnpay.notificationmicroservice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {
    private final Map<String, OtpDataDto> otpCache = new ConcurrentHashMap<>();
    private final EmailService emailService;

    @Autowired
    public OtpServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public String generateRandomOtp(int length) {
        String numbers = "0123456789";
        Random randomObject = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(randomObject.nextInt(10)); // cast to character
        }

        return new String(otp);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        OtpDataDto otpData = otpCache.get(email);
        if (otpData != null && Objects.equals(otpData.getOtp(), otp)) {
            if (System.currentTimeMillis() - otpData.getTimestamp() <= 300000) {
                otpCache.remove(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<?> sendOtp(String email, String otp) {
        otpCache.put(email, new OtpDataDto(otp, System.currentTimeMillis()));
        String text = "Hey! \n Your Otp for Reset Password is " + otp
                + "\n This otp will be invalid after 5 minutes. \nRegards,\n Safat";

        EmailDetailsDto emailDetails = new EmailDetailsDto();
        emailDetails.setRecipient(email);
        emailDetails.setMsgBody(text);
        emailDetails.setSubject("OTP from PSO Service");

        return emailService.sendSyncEmail(emailDetails);
    }

    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        String otp = this.generateRandomOtp(6);
        return this.sendOtp(forgetPasswordRequest.getEmail(), otp);
    }

}
