package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class NotOperation implements ImageFilter{
    @Override
    public ImageMatrix apply(ImageMatrix img) {
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                out.r[y][x] = 255 - img.r[y][x];
                out.g[y][x] = 255 - img.g[y][x];
                out.b[y][x] = 255 - img.b[y][x];
                out.a[y][x] = img.a[y][x];
            }
        }
        return out;
    }
}
