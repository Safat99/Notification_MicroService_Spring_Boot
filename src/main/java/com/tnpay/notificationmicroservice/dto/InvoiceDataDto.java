package com.tnpay.notificationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvoiceDataDto {

    private Integer quantity;
    private String description;
    private Float unit_price;
    private Float amount;
    private Float subtotal;
    private Float sales_tax;
    private Float pnp;
    private Float total_due;

}
