package com.imageProcessor.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SumTwo implements ImageFilter {

    @Override
    public BufferedImage apply2(BufferedImage img, BufferedImage img2) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                Color c2 = new Color(img2.getRGB(j, i));
                int red = Math.min(255, c.getRed() + c2.getRed());
                int green = Math.min(255, c.getGreen() + c2.getGreen());
                int blue = Math.min(255, c.getBlue() + c2.getBlue());
                int alpha = Math.min(255, c.getAlpha() + c2.getAlpha());
                Color nc = new Color(red, green, blue, alpha);
                out.setRGB(j, i, nc.getRGB());
            }
        }
        return out;
    }

    @Override
    public BufferedImage apply(BufferedImage img, int value) {
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }
}
