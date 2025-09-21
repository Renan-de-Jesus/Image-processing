package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class SumTwo implements ImageFilter2 {

    @Override
    public ImageMatrix apply(ImageMatrix img1, ImageMatrix img2) {
        int w = Math.min(img1.getWidth(), img2.getWidth());
        int h = Math.min(img1.getHeight(), img2.getHeight());
        ImageMatrix out = ImageMatrix.empty(w, h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                out.r[y][x] = Math.min(255, img1.r[y][x] + img2.r[y][x]);
                out.g[y][x] = Math.min(255, img1.g[y][x] + img2.g[y][x]);
                out.b[y][x] = Math.min(255, img1.b[y][x] + img2.b[y][x]);
                out.a[y][x] = Math.max(img1.a[y][x], img2.a[y][x]);
            }
        }
        return out;
    }
}
