package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Sum implements ParametricImageFilter {

    @Override
    public ImageMatrix apply(ImageMatrix img, int value) {
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.r[y][x] = clamp(img.r[y][x] + value);
                out.g[y][x] = clamp(img.g[y][x] + value);
                out.b[y][x] = clamp(img.b[y][x] + value);
                out.a[y][x] = img.a[y][x];
            }
        }
        return out;
    }

    private int clamp(int v) {
        return Math.max(0, Math.min(255, v));
    }
}

