package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface EmailService{

    CompletableFuture<ResponseEntity<EmailResponse>> sendAsyncEmail(EmailDetailsDto emailDetails);

    ResponseEntity<String>  sendSyncEmail(EmailDetailsDto emailDetails);

    CompletableFuture<EmailResponse> sendAsyncEmail2(EmailDetailsDto emailDetails);

    ResponseEntity<?> sendFileToEmail(MultipartFile file, EmailDetailsDto emailDetailsDto);

    ResponseEntity<?> sendFileToEmail2(MultipartFile file, String recipient, String subject, String msgBody);
}