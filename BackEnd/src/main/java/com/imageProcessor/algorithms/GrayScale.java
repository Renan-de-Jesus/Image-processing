package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class GrayScale implements ImageFilter {
    @Override
    public ImageMatrix apply(ImageMatrix img) {
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                out.r[i][j] = (img.r[i][j] + img.g[i][j] + img.b[i][j]) / 3;
                out.g[i][j] = out.r[i][j];
                out.b[i][j] = out.r[i][j];
                out.a[i][j] = img.a[i][j];
            }
        }
        return out;
    }
}
