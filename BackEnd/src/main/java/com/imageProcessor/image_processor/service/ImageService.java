package com.imageProcessor.image_processor.service;

import com.imageProcessor.algorithms.*;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageService {
        public BufferedImage processImage(BufferedImage img1, BufferedImage img2, String operation, int value) throws IOException {
        ImageFilter filter = null;

        if(img2 != null){
            operation = "sumtwo";
        }

        switch (operation.toLowerCase()) {
            case "sum":
                filter = new Sum(value);
                break;
            case "sumtwo":;
                break;
            default:
                return img1;
        }

        if (filter != null) {
            return filter.apply(img1, value);
        }

        return img1;
    }
}
