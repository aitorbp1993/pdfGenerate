package com.example.generator.service.impl;

import com.example.generator.dto.EmailRequest;
import com.example.generator.error.CustomException;
import com.example.generator.service.PdfService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    private final JavaMailSender mailSender;

    public PdfServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void generateAndSendPdf(EmailRequest emailRequest) throws CustomException, Exception {
        validateImageFile(emailRequest.getImage());

        byte[] pdfBytes = generatePdf(emailRequest.getImage());

        sendEmailWithAttachment(emailRequest.getEmail(), emailRequest.getSubject(), emailRequest.getMessage(), pdfBytes);
    }

    private void validateImageFile(MultipartFile image) throws CustomException {
        if (image.isEmpty() || (!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg"))) {
            throw new CustomException("El archivo debe ser una imagen en formato PNG o JPEG.");
        }
    }

    private byte[] generatePdf(MultipartFile image) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            ImageData imageData = ImageDataFactory.create(image.getBytes());
            Image pdfImage = new Image(imageData).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

            document.add(pdfImage);
            document.close();

            return out.toByteArray();
        }
    }

    private void sendEmailWithAttachment(String email, String subject, String message, byte[] pdfBytes) throws CustomException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("abartolomep01@educarex.es");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message);

            helper.addAttachment("imagen.pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
