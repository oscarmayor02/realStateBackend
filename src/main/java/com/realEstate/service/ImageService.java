package com.realEstate.service;

import com.realEstate.model.Image;

// Interface to handle image operations
public interface ImageService {
    Image saveImage(Image image); // Save image metadata (usually the URL from storage)
}