package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmailService{

    CompletableFuture<ResponseEntity<EmailResponse>> sendAsyncEmail(EmailDetailsDto emailDetails);

    ResponseEntity<String>  sendSyncEmail(EmailDetailsDto emailDetails);

    CompletableFuture<EmailResponse> sendAsyncEmail2(EmailDetailsDto emailDetails);

    ResponseEntity<EmailResponse> sendFileToEmail(MultipartFile file, EmailDetailsDto emailDetailsDto);

    ResponseEntity<?> sendFileToEmail2(MultipartFile file, String recipient, String subject, String msgBody);

    CompletableFuture<EmailResponse> asyncSendFileToEmail(MultipartFile file, EmailDetailsDto emailDetails);

    ResponseEntity<EmailResponse> generateInvoiceAndSendEmail(List<InvoiceDataDto> invoiceDataDtoList, EmailDetailsDto emailDetailsDto, Integer invoiceNo) throws JRException;
}