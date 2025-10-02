package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Maximum implements ImageFilter{
    @Override
    public ImageMatrix apply(ImageMatrix img){
        ImageMatrix out = ImageMatrix.empty(img.getWidth(), img.getHeight());

        for(int y = 1; y < img.getHeight(); y++){
            for(int x = 1; x < img.getWidth(); x++){
                int max[] = new int[9];
                max[0] = out.r[y-1][x-1];
                max[1] = out.r[y-1][x];
                max[2] = out.r[y-1][x+1];
                max[3] = out.r[y][x-1];
                max[4] = out.r[y][x];
                max[5] = out.r[y][x]+1;
                max[6] = out.r[y+1][x-1];
                max[7] = out.r[y+1][x];
                max[8] = out.r[y+1][x+1];

                for(int i = 0; i <= max.length; x++){
                    int bigger = 0;
                    if(max[i] > bigger){
                        bigger = max[i];
                    }
                    out.r[y][x] = bigger;
                }

                max[0] = out.g[y-1][x-1];
                max[1] = out.g[y-1][x];
                max[2] = out.g[y-1][x+1];
                max[3] = out.g[y][x-1];
                max[4] = out.g[y][x];
                max[5] = out.g[y][x]+1;
                max[6] = out.g[y+1][x-1];
                max[7] = out.g[y+1][x];
                max[8] = out.g[y+1][x+1];

                for(int i = 0; i <= max.length; x++){
                    int bigger = 0;
                    if(max[i] > bigger){
                        bigger = max[i];
                    }
                    out.g[y][x] = bigger;
                }
                max[0] = out.b[y-1][x-1];
                max[1] = out.b[y-1][x];
                max[2] = out.b[y-1][x+1];
                max[3] = out.b[y][x-1];
                max[4] = out.b[y][x];
                max[5] = out.b[y][x]+1;
                max[6] = out.b[y+1][x-1];
                max[7] = out.b[y+1][x];
                max[8] = out.b[y+1][x+1];

                for(int i = 0; i <= max.length; x++){
                    int bigger = 0;
                    if(max[i] > bigger){
                        bigger = max[i];
                    }
                    out.b[y][x] = bigger;
                }
                out.a[y][x] = img.a[y][x];
            }
        }
        return out;
    }
}