package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Blending implements ImageFilter3 {
    @Override
    public ImageMatrix apply(ImageMatrix img1, ImageMatrix img2, int value) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());

        for (int i = 0; i < img1.getHeight(); i++) {
            for (int j = 0; j < img1.getWidth(); j++) {
                out.r[i][j] = clamp((value * img1.r[i][j] + (100 - value) * img2.r[i][j]) / 100);
                out.g[i][j] = clamp((value * img1.g[i][j] + (100 - value) * img2.g[i][j]) / 100);
                out.b[i][j] = clamp((value * img1.b[i][j] + (100 - value) * img2.b[i][j]) / 100);
                out.a[i][j] = img1.a[i][j];
            }
        }
        return out;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
