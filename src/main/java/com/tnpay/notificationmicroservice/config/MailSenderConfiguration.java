package com.tnpay.notificationmicroservice.config;

import com.tnpay.notificationmicroservice.MailProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
public class MailSenderConfiguration {

    @Autowired
    private MailProperties mailProperties;

    /**
     * using a bean so that our JavaMailSender can be read data from mail.properties
     * @return a JavaMailSender
     */
    @Bean
    public JavaMailSender emailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties javaMailProperties = mailSender.getJavaMailProperties();
        javaMailProperties.put("mail.smtp.auth", mailProperties.getSmtpAuth());
        javaMailProperties.put("mail.protocol", mailProperties.getTransportProtocol());
        javaMailProperties.put("mail.smtp.starttls.enable", mailProperties.getEnableSmtpStartTls());
        javaMailProperties.put("mail.smtp.ssl.trust", mailProperties.getSmtpSslTrust());

        return mailSender;
    }
}
