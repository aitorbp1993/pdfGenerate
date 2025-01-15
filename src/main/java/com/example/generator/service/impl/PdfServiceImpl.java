package com.example.generator.service.impl;

import com.example.generator.service.PdfService;
import com.example.generator.error.CustomException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] generatePdf(MultipartFile image) throws CustomException {
        validateImageFile(image);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            ImageData imageData = ImageDataFactory.create(image.getBytes());
            Image pdfImage = new Image(imageData).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

            document.add(pdfImage);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new CustomException("Error al generar el PDF", e);
        }
    }

    private void validateImageFile(MultipartFile image) throws CustomException {
        if (image.isEmpty() || (!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg"))) {
            throw new CustomException("El archivo debe ser una imagen en formato PNG o JPEG.");
        }
      
    }
}
