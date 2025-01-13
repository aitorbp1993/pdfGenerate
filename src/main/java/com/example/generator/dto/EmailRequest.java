package com.example.generator.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmailRequest {
    private String email;
    private String subject;
    private String message;
    private MultipartFile image;
}
