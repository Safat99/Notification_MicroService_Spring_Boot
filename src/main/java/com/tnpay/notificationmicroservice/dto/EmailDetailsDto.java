package com.tnpay.notificationmicroservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDto {

    @NotNull(message = "recipient cannot be null")
    @Size(max = 50)
    @Email(message = "must be a valid email")
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}

