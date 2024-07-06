package com.atwoz.alert.config;

import com.atwoz.alert.exception.exceptions.FirebaseFileNotFoundException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private static final String FIREBASE_URL = "src/main/resources/firebase/firebase_key.json";

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            FileInputStream firebaseFile = new FileInputStream(FIREBASE_URL);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(firebaseFile))
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new FirebaseFileNotFoundException();
        }
    }
}
