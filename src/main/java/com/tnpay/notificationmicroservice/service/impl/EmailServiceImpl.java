package com.tnpay.notificationmicroservice.service.impl;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.exception.MailSendingException;
import com.tnpay.notificationmicroservice.service.EmailService;
import com.tnpay.notificationmicroservice.utils.MailSenderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailSenderUtils mailSenderUtils;
    @Value("${spring.mail.username}")
    private static String sender;

    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public EmailServiceImpl(MailSenderUtils mailSenderUtils) {
        this.mailSenderUtils = mailSenderUtils;
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<EmailResponse>> sendAsyncEmail(EmailDetailsDto emailDetails) throws MailException {
//        CompletableFuture<String> future = new CompletableFuture<>();
        return CompletableFuture.supplyAsync(() -> {
            EmailResponse emailResponse = new EmailResponse();
            try {
                mailSenderUtils.mailSending(emailDetails,sender);
                emailResponse.setResult("done");

                return ResponseEntity.ok(emailResponse);
            } catch (MailException e) {
                emailResponse.setResult("not done!");

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
            }

        });
    }

    @Override
    public ResponseEntity<String> sendSyncEmail(EmailDetailsDto emailDetails) {
        try {
            mailSenderUtils.mailSending(emailDetails, sender);

            return ResponseEntity.ok("Email sent successfully...");
        }
        catch (MailException e) {
            throw new MailSendingException("Failed to send email...", e);
        }
    }

    @Override
    @Async
    public CompletableFuture<EmailResponse> sendAsyncEmail2(EmailDetailsDto emailDetails) {
    // for achieving non-blocking behaviour

        long start = System.currentTimeMillis();
        EmailResponse emailResponse = new EmailResponse();
        try{
            CompletableFuture<Void> emailSendingFuture = mailSenderUtils.mailSendingFuture(emailDetails,sender); // async method
            emailResponse.setResult("email sending process started in the background");
            logger.info("email sending process started under " + Thread.currentThread().getName());
            long end = System.currentTimeMillis();
            logger.info("Total time {} sec", (end - start));
//                return ResponseEntity.ok(emailResponse);
                return CompletableFuture.completedFuture(emailResponse);
//            return emailSendingFuture.thenApplyAsync( result -> true)
//                    .exceptionally(ex -> {
////                            ex.printStackTrace();
//                        return false;
//                    });
        } catch (MailException e) {
            emailResponse.setResult("not done!");

//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emailResponse);
            return CompletableFuture.completedFuture(emailResponse);
        }
    }

    @Override
    public ResponseEntity<?> sendInvoiceToEmail(MultipartFile file, EmailDetailsDto emailDetailsDto) {


        return null;
    }


}
