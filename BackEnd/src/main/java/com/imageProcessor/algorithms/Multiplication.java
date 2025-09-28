package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Multiplication implements ParametricImageFilter {
    @Override
    public ImageMatrix apply(ImageMatrix img, int value) {
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());
        
        double factor = value / 100.0; 
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.r[y][x] = clamp((int) (img.r[y][x] * factor));
                out.g[y][x] = clamp((int) (img.g[y][x] * factor));
                out.b[y][x] = clamp((int) (img.b[y][x] * factor));
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
