package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/notification")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-async-email")
    public CompletableFuture<ResponseEntity<EmailResponse>> sendEmail(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendAsyncEmail(emailDetails);
    }


    @PostMapping("/send-sync-mail")
    public ResponseEntity<String> sendSyncEmail(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendSyncEmail(emailDetails);
    }

    @PostMapping("/send-async-email-fast-response")
    public CompletableFuture<EmailResponse> sendAsyncEmail2(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendAsyncEmail2(emailDetails);
    }

    @PostMapping("/send-invoice-to-email-sync")
    public ResponseEntity<?> sendInvoiceToEmail(@RequestParam("file") MultipartFile file, @Valid @RequestBody EmailDetailsDto emailDetailsDto) {
        return emailService.sendInvoiceToEmail(file,emailDetailsDto);
    }


}
