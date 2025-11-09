package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class XorOperation implements ImageFilter2{
    @Override
    public ImageMatrix apply(ImageMatrix img1, ImageMatrix img2) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                out.r[y][x] = img1.r[y][x] ^ img2.r[y][x];
                out.g[y][x] = img1.g[y][x] ^ img2.g[y][x];
                out.b[y][x] = img1.b[y][x] ^ img2.b[y][x];
                out.a[y][x] = img1.a[y][x];
            }
        }
        return out;
    }
    
}
