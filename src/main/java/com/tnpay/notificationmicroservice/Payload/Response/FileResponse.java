package com.tnpay.notificationmicroservice.Payload.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponse {

    private String message;
    private String file_size;
}
