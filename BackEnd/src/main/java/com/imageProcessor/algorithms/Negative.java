package com.imageProcessor.algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Negative implements  ImageFilter{

    @Override
    public BufferedImage apply(BufferedImage img, int value) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                int red = Math.min(255, c.getRed());
                int green = Math.min(255, c.getGreen());
                int blue = Math.min(255, c.getBlue());
                int alpha = Math.min(255, c.getAlpha());

                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                alpha = 255 - alpha;
                
                Color nc = new Color(red, green, blue, alpha);
                out.setRGB(j, i, nc.getRGB());
            }
        }
        return out;
    }

    @Override
    public BufferedImage apply2(BufferedImage img, BufferedImage img2) {
        throw new UnsupportedOperationException("Unimplemented method 'apply2'");
    }
}
