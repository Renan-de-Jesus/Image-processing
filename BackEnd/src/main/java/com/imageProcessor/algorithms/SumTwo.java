package com.imageProcessor.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SumTwo implements ImageFilter{

    @Override
    public BufferedImage apply2(BufferedImage img, BufferedImage img2) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
            }
        }
        return out;
    }

    @Override
    public BufferedImage apply(BufferedImage img, int value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apply'");
    }
}
