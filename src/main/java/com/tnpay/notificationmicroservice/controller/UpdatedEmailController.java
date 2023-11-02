package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.dto.GenerateAndSendPdfToEmailDto;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import com.tnpay.notificationmicroservice.service.EmailService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/notification-updated")
public class UpdatedEmailController {
    private final EmailService emailService;

    @Autowired
    public UpdatedEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendSyncEmail(emailDetails);
    }

    @PostMapping("/send-file-to-email")
    public ResponseEntity<EmailResponse> sendFileToEmail(@RequestPart("file") MultipartFile file,
                                                         @Valid @RequestPart("emailDetailsDTO") EmailDetailsDto emailDetailsDto) {
        return emailService.sendFileToEmail(file,emailDetailsDto);
    }

    @PostMapping("/generate-invoice-and-send-email")
    public ResponseEntity<EmailResponse> generateInvoiceAndSendEmail(@RequestBody GenerateAndSendPdfToEmailDto dto) throws JRException {
        return emailService.generateInvoiceAndSendEmail(dto.getInvoiceDataDtoList(), dto.getEmailDetailsDto(), dto.getInvoiceNo());
    }
}
