package com.imageProcessor.image_processor.service;

import com.imageProcessor.algorithms.*;
import com.imageProcessor.image_processor.util.ImageMatrix;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

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

    public ImageMatrix process(ImageMatrix img1, ImageMatrix img2, String operation, int value) {

        if (value == 0 && img2 != null) {
            operation = "sumtwo";
        }

        switch (operation.toLowerCase()) {
            case "negative":
                return new Negative().apply(img1);

            case "sumtwo":
                return new SumTwo().apply(img1, img2);

            case "sum":
                return new Sum().apply(img1, value);

            default:
                return img1;
        }
    }
}
