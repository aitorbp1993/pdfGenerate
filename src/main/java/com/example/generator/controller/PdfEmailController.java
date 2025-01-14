package com.example.generator.controller;

import com.example.generator.dto.EmailRequest;
import com.example.generator.service.EmailService;
import com.example.generator.service.PdfService;
import com.example.generator.error.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pdf-email")
@Validated
public class PdfEmailController {

    private final PdfService pdfService;
    private final EmailService emailService;

    public PdfEmailController(PdfService pdfService, EmailService emailService) {
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    @PostMapping("/generate-and-send")
    public ResponseEntity<String> generateAndSendPdf(@ModelAttribute @Valid EmailRequest emailRequest) {
        try {
            byte[] pdfBytes = pdfService.generatePdf(emailRequest.getImage());
            emailService.sendEmailWithAttachment(emailRequest, pdfBytes);
            return ResponseEntity.ok("Correo enviado correctamente.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }
}
