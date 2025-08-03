/*
package com.realEstate.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

*/
/**
 * Configuration class to initialize Firebase Admin SDK
 * when the Spring Boot application starts.
 *//*

@Configuration
public class FirebaseConfig {

    */
/**
     * This method will be called after the Spring context is initialized.
     * It sets up Firebase using the service account key file.
     *//*

    @PostConstruct
    public void init() throws IOException {
        // Load the service account JSON file from your project directory.
        // Make sure this file is NOT exposed publicly and is excluded from Git.
        FileInputStream serviceAccount =
                new FileInputStream("firebase/realestateapp-de860-firebase-adminsdk-fbsvc-64bf247ea4.json");

        // Build the Firebase options with credentials and storage bucket
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)) // Sets credentials from service account
                .setStorageBucket("realestateapp-de860.appspot.com") // This must match exactly with your Firebase Storage bucket name
                .build();

        // Initialize Firebase only if no other FirebaseApp has been created
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("Attempting to initialize Firebase with bucket: " + options.getStorageBucket());
        }
    }
}
*/
