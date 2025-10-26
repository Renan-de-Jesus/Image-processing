package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Division implements ParametricImageFilter {
    @Override
    public ImageMatrix apply(ImageMatrix img, int value) {
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());
        
        if (value == 0) {
            value = 1;
        }
        
        double divisor = value / 100.0;
        if (divisor == 0) {
            divisor = 0.01;
        }
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.r[y][x] = clamp((int) (img.r[y][x] / divisor));
                out.g[y][x] = clamp((int) (img.g[y][x] / divisor));
                out.b[y][x] = clamp((int) (img.b[y][x] / divisor));
                out.a[y][x] = img.a[y][x];
            }
        }
        return out;
    }

    private int clamp(int v) {
        if (v < 0)
            return 0;
        if (v > 255)
            return 255;
        return v;
    }
}