package com.tnpay.notificationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemReportDataDto {

    private Long id;
    private String itemName;
    private Float price;
    private Integer qty;
    private Float total;
}
