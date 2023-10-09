package com.tnpay.notificationmicroservice.service.impl;

import com.tnpay.notificationmicroservice.Payload.Response.FileResponse;
import com.tnpay.notificationmicroservice.dto.FileExtensionDto;
import com.tnpay.notificationmicroservice.dto.InvoiceDataDto;
import com.tnpay.notificationmicroservice.dto.ItemReportDataDto;
import com.tnpay.notificationmicroservice.exception.BadRequestException;
import com.tnpay.notificationmicroservice.service.FileService;
import com.tnpay.notificationmicroservice.utils.FileUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public ResponseEntity<FileResponse> save(MultipartFile file) throws IOException {
        FileUtils.isValid(file);
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(System.getProperty("user.dir"), "notification-microservice/src/main/resources/static/uploads", fileName);

        try {
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("File write failed.", e);
        }

        FileResponse fileResponse = FileResponse.builder()
                .file_size(FileUtils.convertFileSize(file.getSize()))
                .message("file uploaded successfully")
                .build();

        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> generatePDF() {
        try {

            List<ItemReportDataDto> itemReportDataDtoList = new ArrayList<>();
            itemReportDataDtoList.add(new ItemReportDataDto(1L, "Item 1", 10.0f, 5, 50.0f));
            itemReportDataDtoList.add(new ItemReportDataDto(2L, "Item 2", 20.0f, 3, 60.0f));
            itemReportDataDtoList.add(new ItemReportDataDto(3L, "Item 3", 5.0f, 8, 40.0f));

            InputStream templateInputStream = getClass().getResourceAsStream("/templates/item-report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateInputStream);

            // Create data source (if needed)
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemReportDataDtoList);

            // Set parameters (if needed)
            Map<String, Object> params = new HashMap<>();

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            // Export the report to a PDF file
            byte[] pdfBytes;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                pdfBytes = outputStream.toByteArray();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<byte[]> generateInvoicePDF(List<InvoiceDataDto> invoiceDataDtoList) {
        try {

            InputStream templateInputStream = getClass().getResourceAsStream("/templates/invoice-report-test1.jrxml");
//            InputStream templateInputStream = getClass().getResourceAsStream("/templates/invoice_report_v1.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateInputStream);

            // Create data source (if needed)
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoiceDataDtoList);

            // Set parameters (if needed)
            Map<String, Object> params = new HashMap<>();
            params.put("invoice_no", 200);
            params.put("CollectionBeanParam", dataSource);

            // Fill the report
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

            // Export the report to a PDF file
            byte[] pdfBytes;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                pdfBytes = outputStream.toByteArray();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Invoice_report.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
