package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class FlipUD implements ImageFilter{
    @Override
    public ImageMatrix apply(ImageMatrix img){
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());

        for(int i = img.getHeight() - 1; i >= 0; i--){
            for(int j = 0; j < img.getWidth(); j++){
                out.r[i][j] = img.r[img.getHeight() - i - 1][j];
                out.g[i][j] = img.g[img.getHeight() - i - 1][j];
                out.b[i][j] = img.b[img.getHeight() - i - 1][j];
                out.a[i][j] = img.a[img.getHeight() - i - 1][j];
            }
        }
        return out;
    }
    
}