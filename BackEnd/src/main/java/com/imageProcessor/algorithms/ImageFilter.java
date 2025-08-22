package com.imageProcessor.algorithms;

import java.awt.image.BufferedImage;

public interface ImageFilter {
    BufferedImage apply(BufferedImage img, int value);

    BufferedImage apply2(BufferedImage img, BufferedImage img2);
}