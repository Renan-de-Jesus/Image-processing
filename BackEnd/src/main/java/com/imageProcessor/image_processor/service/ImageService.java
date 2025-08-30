package com.imageProcessor.image_processor.service;

import com.imageProcessor.algorithms.*;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageService {

    private BufferedImage convertToRGBA(BufferedImage original) {
        if (original == null)
            return null;

        if (original.getType() == BufferedImage.TYPE_INT_ARGB) {
            return original;
        }

        BufferedImage newImage = new BufferedImage(
                original.getWidth(),
                original.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(original, 0, 0, null);
        return newImage;
    }

    public BufferedImage processImage(BufferedImage img1, BufferedImage img2, String operation, int value)
            throws IOException {
        ImageFilter filter = null;

        if (img2 != null) {
            operation = "sumtwo";
        }

        img1 = convertToRGBA(img1);
        img2 = convertToRGBA(img2);

        switch (operation.toLowerCase()) {
            case "sum":
                filter = new Sum(value);
                break;
            case "sumtwo":
                ;
                filter = new SumTwo();
                break;
            default:
                return img1;
        }

        if (img2 != null) {
            return filter.apply2(img1, img2);
        } else {
            return img1;
        }
    }
}
