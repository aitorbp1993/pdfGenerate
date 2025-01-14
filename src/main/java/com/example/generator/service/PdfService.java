package com.example.generator.service;

import com.example.generator.error.CustomException;
import org.springframework.web.multipart.MultipartFile;

public interface PdfService {
    byte[] generatePdf(MultipartFile image) throws CustomException, Exception;
}
