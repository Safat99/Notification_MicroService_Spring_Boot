package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.request.ForgetPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface EmailService{

    CompletableFuture<ResponseEntity<EmailResponse>> sendAsyncEmail(EmailDetailsDto emailDetails);

    ResponseEntity<String>  sendSyncEmail(EmailDetailsDto emailDetails);

    CompletableFuture<EmailResponse> sendAsyncEmail2(EmailDetailsDto emailDetails);

    ResponseEntity<?> sendInvoiceToEmail(MultipartFile file, EmailDetailsDto emailDetailsDto);
}