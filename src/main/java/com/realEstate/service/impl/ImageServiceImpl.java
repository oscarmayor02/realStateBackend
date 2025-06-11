package com.realEstate.service.impl;

import com.realEstate.model.Image;
import com.realEstate.repository.ImageRepository;
import com.realEstate.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service for handling image saving logic
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // Persist image data (e.g., URL) to database
    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
