package com.imageProcessor.image_processor.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageMatrix {
    private final int width;
    private final int height;

    public final int[][] r;
    public final int[][] g;
    public final int[][] b;
    public final int[][] a;

    private ImageMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        r = new int[height][width];
        g = new int[height][width];
        b = new int[height][width];
        a = new int[height][width];
    }

    public static ImageMatrix fromBufferedImage(BufferedImage img) {
        if (img == null) return null;
        int w = img.getWidth();
        int h = img.getHeight();
        ImageMatrix m = new ImageMatrix(w, h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = img.getRGB(x, y);
                m.a[y][x] = (argb >> 24) & 0xFF;
                m.r[y][x] = (argb >> 16) & 0xFF;
                m.g[y][x] = (argb >> 8) & 0xFF;
                m.b[y][x] = argb & 0xFF;
            }
        }
        return m;
    }

    public BufferedImage toBufferedImage() {
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aa = clamp(a[y][x]);
                int rr = clamp(r[y][x]);
                int gg = clamp(g[y][x]);
                int bb = clamp(b[y][x]);
                int argb = (aa << 24) | (rr << 16) | (gg << 8) | bb;
                out.setRGB(x, y, argb);
            }
        }
        return out;
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 255) return 255;
        return v;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public static ImageMatrix empty(int width, int height) {
        return new ImageMatrix(width, height);
    }
}
