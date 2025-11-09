package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Threshold implements ParametricImageFilter{
    @Override
    public ImageMatrix apply(ImageMatrix img1, int value) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {

                int gray = (img1.r[y][x] + img1.g[y][x] + img1.b[y][x]) / 3;
                out.r[y][x] = (gray >= value) ? 255 : 0;
                out.g[y][x] = (gray >= value) ? 255 : 0;
                out.b[y][x] = (gray >= value) ? 255 : 0;
                out.a[y][x] = img1.a[y][x];
            }
        }
        return out;
    }
    
}
