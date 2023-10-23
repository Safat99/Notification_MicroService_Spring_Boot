package com.tnpay.notificationmicroservice.utils;

import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.exception.MailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class MailSenderUtils {

//    @Value("${spring.mail.username}")
//    private String sender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    Logger logger = LoggerFactory.getLogger(MailSenderUtils.class);

    public MailSenderUtils(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void mailSending(EmailDetailsDto emailDetails, String sender) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetails.getRecipient());
        mailMessage.setText(emailDetails.getMsgBody());
        mailMessage.setSubject(emailDetails.getSubject());

        javaMailSender.send(mailMessage);
    }

    @Async
    public CompletableFuture<Void> mailSendingFuture(EmailDetailsDto emailDetails, String sender) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());
            javaMailSender.send(mailMessage);

            future.complete(null);
        } catch (Exception e) {
            throw new MailSendingException("future mail catch block: sending failed", e);
        }

        return future;
    }

    public String mailSendingWithAttachment(EmailDetailsDto emailDetails, String sender, MultipartFile file) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            // rendering email template with dynamic data using thymeleaf
            Context context = new Context();
            context.setVariables(setVariablesOnEmailTemplate(emailDetails));
            String htmlContent = templateEngine.process("emailTemplate", context);
            mimeMessageHelper.setText(htmlContent, true);
//            mimeMessageHelper.setText(emailDetails.getMsgBody(), true);

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            javaMailSender.send(mimeMessage);

            return "Mail Sent Successfully";
        } catch (MessagingException e ) {
            throw new MailSendingException("Error while sending email!!", e);
        }
    }

    @Async
    public CompletableFuture<Void> asyncMailSendingWithAttachment(EmailDetailsDto emailDetails,
                                                                  String sender, MultipartFile file) {
//        CompletableFuture<Void> future = new CompletableFuture<>();
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
        System.out.println("2nd async started");
        logger.info("2nd async started");

        try {
            System.out.println("2nd async started");
            logger.info("2nd async started");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            logger.info("create MIME message object done");

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            logger.info("120 number line passed");

            // rendering email template with dynamic data using thymeleaf
            Context context = new Context();
            context.setVariables(setVariablesOnEmailTemplate(emailDetails));
            String htmlContent = templateEngine.process("emailTemplate", context);
            mimeMessageHelper.setText(htmlContent, true);
//            mimeMessageHelper.setText(emailDetails.getMsgBody(), true);

            logger.info("128 number line passed");

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            javaMailSender.send(mimeMessage);

            System.out.println("2nd async end");
            logger.info("2nd async ended");

            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
//            throw new MailSendingException("future mail catch block: sending failed", e);
            logger.info("async thread error", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private Map<String, Object> setVariablesOnEmailTemplate(EmailDetailsDto emailDetails) {
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("recipientName", emailDetails.getRecipient());
        templateVariables.put("requestId", 100);
        templateVariables.put("requestDate", LocalDateTime.now());
        templateVariables.put("senderName", "PSO Team, TechnoNext");
        return templateVariables;
    }

}
