package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Media implements ImageFilter2 {
    @Override
    public ImageMatrix apply(ImageMatrix img1, ImageMatrix img2) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());

        System.out.println("Image1: " + img1.getWidth() + "x" + img1.getHeight());
        System.out.println("Image2: " + img2.getWidth() + "x" + img2.getHeight());

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                out.r[y][x] = clamp((img1.r[y][x] + img2.r[y][x]) / 2);
                out.g[y][x] = clamp((img1.g[y][x] + img2.g[y][x]) / 2);
                out.b[y][x] = clamp((img1.b[y][x] + img2.b[y][x]) / 2);
                out.a[y][x] = clamp((img1.a[y][x] + img2.a[y][x]) / 2);
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
