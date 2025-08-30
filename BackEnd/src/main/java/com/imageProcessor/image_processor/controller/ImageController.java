package com.imageProcessor.image_processor.controller;

import org.springframework.http.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imageProcessor.image_processor.service.ImageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    private BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resized;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> processImage(
            @RequestParam(name = "file1", required = false) MultipartFile file1,
            @RequestParam(name = "file2", required = false) MultipartFile file2,
            @RequestParam(name = "operation", required = true) String operation,
            @RequestParam(name = "value", required = false) String valueStr) throws IOException {

        if (file1 == null && file2 == null) {
            return ResponseEntity.badRequest().body("Pelo menos um arquivo deve ser enviado.");
        }

        int value = 0;

        if (valueStr != null && !valueStr.isBlank()) {
            try {
                value = Integer.parseInt(valueStr);
            } catch (NumberFormatException e) {
                return ResponseEntity
                        .badRequest()
                        .body("Valor inválido: deve ser um número inteiro.");
            }
        }

        BufferedImage img1 = file1 != null ? ImageIO.read(file1.getInputStream()) : null;
        BufferedImage img2 = file2 != null ? ImageIO.read(file2.getInputStream()) : null;

        if (img1 != null && img2 != null) {
            img2 = resizeImage(img2, img1.getWidth(), img1.getHeight());
        }

        BufferedImage result = imageService.processImage(img1, img2, operation, value);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
