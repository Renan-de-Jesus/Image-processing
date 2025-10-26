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

        if (img2 != null && ("sum".equalsIgnoreCase(operation) && value == 0)) {
            operation = "sumtwo";
        }

        if(img2 != null && ("subtraction".equalsIgnoreCase(operation) && value == 0)) {
            operation = "subtractiontwo";
        }

        switch (operation.toLowerCase()) {
            case "sum":
                return new Sum().apply(img1, value);
            case "sumtwo":
                return new SumTwo().apply(img1, img2);
            case "subtraction":
                return new Subtraction().apply(img1, value);
            case "subtractiontwo":
                return new SubtractionTwo().apply(img1, img2);
            case "multiplication":
                return new Multiplication().apply(img1, value);
            case "division":
                return new Division().apply(img1, value);
            case "negative":
                return new Negative().apply(img1);
            case "grayscale":
                return new GrayScale().apply(img1);
            case "fliplr":
                return new FlipLR().apply(img1);

            default:
                return img1;
        }
    }
}
