package com.example.generator.controller;

import com.example.generator.dto.EmailRequest;
import com.example.generator.service.PdfService;
import com.example.generator.error.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate-and-send")
    public ResponseEntity<String> generateAndSendPdf(@ModelAttribute EmailRequest emailRequest) {
        try {
            pdfService.generateAndSendPdf(emailRequest);
            return ResponseEntity.ok("Correo enviado correctamente.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado: " + e.getMessage());
        }
    }
}
