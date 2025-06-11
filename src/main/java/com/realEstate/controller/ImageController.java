package com.realEstate.controller;
import com.realEstate.model.Image;
import com.realEstate.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Marks this class as a Spring REST controller, handling HTTP requests and responses
@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/images")
public class ImageController {

    // Injects the ImageService to handle business logic related to images
    @Autowired
    private ImageService imageService;

    // Documents the endpoint for uploading image metadata
    @Operation(summary = "Upload image metadata")
    // Maps HTTP POST requests to /api/images
    @PostMapping
    public Image uploadImage(@RequestBody Image image) {
        // Saves the image metadata received in the request body and returns it
        return imageService.saveImage(image);
    }
}