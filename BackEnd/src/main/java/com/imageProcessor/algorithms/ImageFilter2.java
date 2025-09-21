package com.imageProcessor.algorithms;

import com.imageProcessor.image_processor.util.ImageMatrix;

public interface ImageFilter2 {
    ImageMatrix apply(ImageMatrix img1, ImageMatrix img2);

}
