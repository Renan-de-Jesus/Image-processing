package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public interface ParametricImageFilter {
    ImageMatrix apply(ImageMatrix img, int value);
}

