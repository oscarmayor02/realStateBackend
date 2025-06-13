/*
package com.realEstate.controller;


import com.realEstate.service.impl.FirebaseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private FirebaseImageService firebaseImageService;

    // Handles image uploads
    @PostMapping("/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        return firebaseImageService.uploadImage(file);
    }
}*/
