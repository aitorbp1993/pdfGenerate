package com.example.generator.service;

import com.example.generator.dto.EmailRequest;
import com.example.generator.error.CustomException;

public interface PdfService {
    void generateAndSendPdf(EmailRequest emailRequest) throws CustomException, Exception;
}
