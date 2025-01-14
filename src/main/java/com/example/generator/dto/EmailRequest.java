package com.example.generator.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class EmailRequest {
    @Email(message = "El correo electrónico no tiene un formato válido.")
    @NotBlank(message = "El correo electrónico es obligatorio.")
    private String email;

    @NotBlank(message = "El asunto es obligatorio.")
    @Size(max = 100, message = "El asunto no puede tener más de 100 caracteres.")
    private String subject;

    @NotBlank(message = "El mensaje es obligatorio.")
    private String message;

    @NotNull(message = "La imagen es obligatoria.")
    private MultipartFile image;
}
