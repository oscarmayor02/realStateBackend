package com.realEstate.service.impl;

import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseImageService {

    // Uploads an image to Firebase and returns the public URL
    public String uploadImage(MultipartFile file) throws IOException {
        // Generate a unique filename
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        // Upload the file to Firebase Storage
        StorageClient.getInstance().bucket()
                .create(fileName, file.getInputStream(), file.getContentType());

        // Return the public URL (if rules allow it)
        return "https://firebasestorage.googleapis.com/v0/b/" +
                StorageClient.getInstance().bucket().getName() +
                "/o/" + fileName.replace("/", "%2F") + "?alt=media";
    }
}