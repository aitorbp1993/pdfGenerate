package com.example.generator.service.impl;

import com.example.generator.dto.EmailRequest;
import com.example.generator.error.CustomException;
import com.example.generator.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import jakarta.validation.Valid; 

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailWithAttachment(@Valid EmailRequest emailRequest, byte[] pdfBytes) throws CustomException, Exception {
        validateEmailRequest(emailRequest);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("abartolomep01@educarex.es");
            helper.setTo(emailRequest.getEmail());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getMessage());

            helper.addAttachment("imagen.pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }

    private void validateEmailRequest(EmailRequest emailRequest) throws CustomException {
        if (!StringUtils.hasText(emailRequest.getEmail()) || !emailRequest.getEmail().contains("@")) {
            throw new CustomException("El correo electrónico no tiene un formato válido.");
        }
        if (!StringUtils.hasText(emailRequest.getSubject())) {
            throw new CustomException("El asunto es obligatorio.");
        }
        if (!StringUtils.hasText(emailRequest.getMessage())) {
            throw new CustomException("El mensaje es obligatorio.");
        }
    }
}
