package com.example.generator.service;

import com.example.generator.dto.EmailRequest;
import com.example.generator.error.CustomException;

public interface EmailService {
    void sendEmailWithAttachment(EmailRequest emailRequest, byte[] pdfBytes) throws CustomException, Exception;
}
