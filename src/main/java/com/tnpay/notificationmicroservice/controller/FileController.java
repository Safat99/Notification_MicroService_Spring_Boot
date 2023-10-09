package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.Payload.Response.FileResponse;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import com.tnpay.notificationmicroservice.service.FileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/notification/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/file-upload")
    public ResponseEntity<FileResponse> insertFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.save(file);
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        return fileService.generatePDF();
    }

    @PostMapping("/generate-test-invoice-pdf")
    public ResponseEntity<byte[]> generateInvoicePDF(@Valid @RequestBody List<InvoiceDataDto> invoiceDataDtoList) {
        return fileService.generateInvoicePDF(invoiceDataDtoList);
    }
}
