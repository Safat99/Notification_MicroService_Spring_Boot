package com.tnpay.notificationmicroservice.service;

import com.tnpay.notificationmicroservice.Payload.Response.FileResponse;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    ResponseEntity<FileResponse> save(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> generatePDF();
    ResponseEntity<byte[]> generateInvoicePDF(List<InvoiceDataDto> invoiceDataDtoList, Integer invoiceNo);

}
