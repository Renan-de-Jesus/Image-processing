package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Histogram implements ImageFilter {
    
    private int[] originalHistogram;
    private int[] processedHistogram;
    
    @Override
    public ImageMatrix apply(ImageMatrix img1) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());
        
        originalHistogram = new int[256];
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int gray = (img1.r[y][x] + img1.g[y][x] + img1.b[y][x]) / 3;
                originalHistogram[gray]++;
            }
        }

        int[] accumulated = new int[256];
        accumulated[0] = originalHistogram[0];
        for (int i = 1; i < 256; i++) {
            accumulated[i] = accumulated[i - 1] + originalHistogram[i];
        }

        int cdfMin = 0;
        for (int i = 0; i < 256; i++) {
            if (accumulated[i] > 0) {
                cdfMin = accumulated[i];
                break;
            }
        }

        int totalPixels = img1.getWidth() * img1.getHeight();
        int[] lookupTable = new int[256];
        for (int i = 0; i < 256; i++) {
            lookupTable[i] = ((accumulated[i] - cdfMin) * 255) / (totalPixels - cdfMin);
        }

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int gray = (img1.r[y][x] + img1.g[y][x] + img1.b[y][x]) / 3;
                int newValue = lookupTable[gray];
                out.r[y][x] = newValue;
                out.g[y][x] = newValue;
                out.b[y][x] = newValue;
                out.a[y][x] = img1.a[y][x];
            }
        }
        
        processedHistogram = new int[256];
        for (int y = 0; y < out.getHeight(); y++) {
            for (int x = 0; x < out.getWidth(); x++) {
                int gray = (out.r[y][x] + out.g[y][x] + out.b[y][x]) / 3;
                processedHistogram[gray]++;
            }
        }

        return out;
    }
    
    public int[] getOriginalHistogram() {
        return originalHistogram;
    }
    
    public int[] getProcessedHistogram() {
        return processedHistogram;
    }
}