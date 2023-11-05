package com.tnpay.notificationmicroservice.controller;

import com.tnpay.notificationmicroservice.Payload.Response.EmailResponse;
import com.tnpay.notificationmicroservice.dto.EmailDetailsDto;
import com.tnpay.notificationmicroservice.dto.GenerateAndSendPdfToEmailDto;
import com.tnpay.notificationmicroservice.service.UpdatedEmailService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notification-updated")
public class UpdatedEmailController {
    private final UpdatedEmailService emailService;

    public UpdatedEmailController(UpdatedEmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * this endpoint is used for sending a plain text mail
     * @param emailDetails
     * @return
     */
    @PostMapping("/send-text-mail")
    public ResponseEntity<String> sendPlainTextEmail(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendPlainTextEmail(emailDetails);
    }

    /**
     * this endpoint is used for sending a markUp text mail which accepts HTML format in the msgBody
     * @param emailDetails
     * @return
     */
    @PostMapping("/send-markup-mail")
    public ResponseEntity<String> sendMarkUpEmail(@Valid @RequestBody EmailDetailsDto emailDetails) {
        return emailService.sendMarkUpEmail(emailDetails);
    }

    /**
     * this endpoint is used for sending an existing file to the email
     * @param file
     * @param emailDetailsDto
     * @return
     */
    @PostMapping("/send-file-to-email")
    public ResponseEntity<EmailResponse> sendFileToEmail(@RequestPart("file") MultipartFile file,
                                                         @Valid @RequestPart("emailDetailsDTO") EmailDetailsDto emailDetailsDto) {
        return emailService.sendFileToEmail(file,emailDetailsDto);
    }

    /**
     * recipient and invoice info will receive,
     * and an invoice file will be instantly generated and sent with the recipient email
     * @param dto
     * @return
     * @throws JRException
     */
    @PostMapping("/generate-invoice-and-send-email")
    public ResponseEntity<EmailResponse> generateInvoiceAndSendEmail(@Valid @RequestBody GenerateAndSendPdfToEmailDto dto) throws JRException {
        return emailService.generateInvoiceAndSendEmail(dto.getInvoiceDataDtoList(), dto.getEmailDetailsDto(), dto.getInvoiceNo());
    }
}
