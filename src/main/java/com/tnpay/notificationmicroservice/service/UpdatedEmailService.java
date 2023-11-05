package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import com.tnpay.notificationmicroservice.exception.MailSendingException;
import com.tnpay.notificationmicroservice.utils.FileUtils;
import com.tnpay.notificationmicroservice.utils.MailSenderUtils;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.tnpay.notificationmicroservice.service.impl.FileServiceImpl.generatePdfByte;

@Service
public class UpdatedEmailService {
    private final MailSenderUtils mailSenderUtils;
    @Value("${mail.username}")
    private String sender;

    public UpdatedEmailService(MailSenderUtils mailSenderUtils) {
        this.mailSenderUtils = mailSenderUtils;
    }

    public ResponseEntity<String> sendPlainTextEmail(EmailDetailsDto emailDetails) {
        try {
            mailSenderUtils.mailSending(emailDetails, sender);

            return ResponseEntity.ok("Email sent successfully...");
        }
        catch (MailException e) {
            throw new MailSendingException("Failed to send email...", e);
        }
    }

    public ResponseEntity<String> sendMarkUpEmail(EmailDetailsDto emailDetails) {
        try {
            mailSenderUtils.markUpMailSending(emailDetails, sender);

            return ResponseEntity.ok("MarkUp Email sent successfully...");
        }
        catch (MailException e) {
            throw new MailSendingException("Failed to send email...", e);
        }
    }

    public ResponseEntity<EmailResponse> sendFileToEmail(MultipartFile file, EmailDetailsDto emailDetailsDto) {
        FileUtils.isValid(file);
        String result = mailSenderUtils.mailSendingWithAttachment(emailDetailsDto, sender, file);
        EmailResponse emailResponse = new EmailResponse(result);
        return ResponseEntity.ok(emailResponse);
    }

    public ResponseEntity<EmailResponse> generateInvoiceAndSendEmail(List<InvoiceDataDto> invoiceDataDtoList, EmailDetailsDto emailDetailsDto, Integer invoiceNo) throws JRException {
        byte[] pdfBytes;
        pdfBytes = generatePdfByte(invoiceDataDtoList, invoiceNo);

        String result = mailSenderUtils.sendPdfToEmail(pdfBytes, sender, emailDetailsDto);
        EmailResponse emailResponse = new EmailResponse(result);
        return ResponseEntity.ok(emailResponse);
    }
}
