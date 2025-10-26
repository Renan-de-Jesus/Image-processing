package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class FlipLR implements ImageFilter{
    @Override
    public ImageMatrix apply(ImageMatrix img){
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());

        for(int i = 0; i < img.getHeight(); i++){
            for(int j = img.getWidth() - 1; j >= 0; j--){
                out.r[i][j] = img.r[i][img.getWidth() - j - 1];
                out.g[i][j] = img.g[i][img.getWidth() - j - 1];
                out.b[i][j] = img.b[i][img.getWidth() - j - 1];
                out.a[i][j] = img.a[i][img.getWidth() - j - 1];
            }
        }
        return out;
    }
    
}