package com.tnpay.notificationmicroservice.utils;

import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.exception.BadRequestException;
import com.tnpay.notificationmicroservice.exception.MailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class MailSenderUtils {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;

    public MailSenderUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
            throw new MailSendingException("future mail catch block stopped: sending failed", e);
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
            mimeMessageHelper.setText(emailDetails.getMsgBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            javaMailSender.send(mimeMessage);

            return "Mail Sent Successfully";
        } catch (MessagingException e ) {
            throw new MailSendingException("Error while sending email!!", e);
        }
    }

}
