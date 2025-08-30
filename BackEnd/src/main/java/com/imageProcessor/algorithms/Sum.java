package com.imageProcessor.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sum implements ImageFilter {

    private int value;

    public Sum(int value) {
        this.value = value;
    }

    @Override
    public BufferedImage apply(BufferedImage img, int value) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                int red = Math.min(255, c.getRed() + value);
                int green = Math.min(255, c.getGreen() + value);
                int blue = Math.min(255, c.getBlue() + value);
                int alpha = Math.min(255, c.getAlpha() + value);
                if(blue > 255) {
                    blue = 255;
                }if(red > 255) {
                    red = 255;
                }if(green > 255) {
                    green = 255;
                }if(alpha > 255){
                    alpha = 255;
                }
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
