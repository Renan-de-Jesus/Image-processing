package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public class Histogram implements ImageFilter {
    @Override
    public ImageMatrix apply(ImageMatrix img1) {
        ImageMatrix out = ImageMatrix.empty(img1.getWidth(), img1.getHeight());
        int[] histogram = new int[256];

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int gray = (img1.r[y][x] + img1.g[y][x] + img1.b[y][x]) / 3;
                histogram[gray]++;
            }
        }

        int[] accumulated = new int[256];
        accumulated[0] = histogram[0];

        for (int i = 1; i < 256; i++) {
            accumulated[i] = accumulated[i - 1] + histogram[i];
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
            if (totalPixels - cdfMin > 0) {
                lookupTable[i] = ((accumulated[i] - cdfMin) * 255) / (totalPixels - cdfMin);
            } else {
                lookupTable[i] = i;
            }
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

        return out;
    }
}
