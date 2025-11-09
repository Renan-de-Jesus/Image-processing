package com.imageProcessor.imageProcessorDTO;

public class HistogramResponse {
    private byte[] image;
    private int[] histogramOriginal;
    private int[] histogramProcessed;

    public HistogramResponse(byte[] image, int[] histogramOriginal, int[] histogramProcessed) {
        this.image = image;
        this.histogramOriginal = histogramOriginal;
        this.histogramProcessed = histogramProcessed;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int[] getHistogramOriginal() {
        return histogramOriginal;
    }

    public void setHistogramOriginal(int[] histogramOriginal) {
        this.histogramOriginal = histogramOriginal;
    }

    public int[] getHistogramProcessed() {
        return histogramProcessed;
    }

    public void setHistogramProcessed(int[] histogramProcessed) {
        this.histogramProcessed = histogramProcessed;
    }
}