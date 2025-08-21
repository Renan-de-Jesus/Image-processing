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

    // Injeção de dependência do serviço
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> processImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("operation") String operation,
            @RequestParam("value") int value) throws IOException {

        // Ler a imagem enviada
        BufferedImage img = ImageIO.read(file.getInputStream());

        // Delegar para o serviço
        BufferedImage result = imageService.processImage(img, operation, value);

        // Converter de volta para byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "png", baos);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
