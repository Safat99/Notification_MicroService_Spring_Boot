package com.tnpay.notificationmicroservice.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateAndSendPdfToEmailDto {

    private List<InvoiceDataDto> invoiceDataDtoList;
    @Valid
    private EmailDetailsDto emailDetailsDto;
    private Integer invoiceNo;
}
