package com.tnpay.notificationmicroservice.exception;

public class MailSendingException extends RuntimeException{

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
