package com.imageProcessor.image_processor.service;

import com.imageProcessor.algorithms.*;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageService {
        public BufferedImage processImage(BufferedImage img, String operation, int value) throws IOException {
        ImageFilter filter = null;

        switch (operation.toLowerCase()) {
            case "sum":
                filter = new Sum(value);
                break;
            case "grayscale":
                //filter = new GrayscaleFilter();
                break;
            default:
                return img; // se não reconhecer, devolve igual
        }

        if (filter != null) {
            return filter.apply(img, value);
        }

        return img;
    }
}
