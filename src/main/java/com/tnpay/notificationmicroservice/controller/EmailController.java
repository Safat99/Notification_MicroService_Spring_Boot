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

    @PostMapping(value = "/send-file-to-email-sync")
    public ResponseEntity<?> sendFileToEmail(@RequestPart("file") MultipartFile file,
                                             @Valid @RequestPart("emailDetailsDTO") EmailDetailsDto emailDetailsDto) {
        return emailService.sendFileToEmail(file,emailDetailsDto);
    }

    @PostMapping(value = "/send-file-to-email-sync2")
    public ResponseEntity<?> sendFileToEmail2(@RequestParam("file") MultipartFile file,
                                              @RequestParam("recipient") String recipient,
                                              @RequestParam("subject") String subject,
                                              @RequestParam("msgBody") String msgBody) {

//    public ResponseEntity<?> sendFileToEmail2(@RequestPart("file") MultipartFile file,
//                                              @Valid @RequestPart("emailDetailsDTO") EmailDetailsDto emailDetailsDto) {
        return emailService.sendFileToEmail2(file,recipient, subject, msgBody);
    }

    @PostMapping(value = "/send-file-to-email-async")
    public CompletableFuture<EmailResponse> sendFileToEmailAsync(@RequestPart("file") MultipartFile file,
                                                                 @Valid @RequestPart("emailDetailsDTO")
                                                                 EmailDetailsDto emailDetailsDto) {
        return emailService.asyncSendFileToEmail(file, emailDetailsDto);
    }




}
