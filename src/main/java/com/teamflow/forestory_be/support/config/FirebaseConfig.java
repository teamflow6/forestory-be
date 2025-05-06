package com.teamflow.forestory_be.support.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.account.credentials}")
    private String credentials;

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            ByteArrayInputStream serviceAccount = new ByteArrayInputStream(
                Base64.getDecoder().decode(credentials)
            );
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }
            return FirebaseApp.getInstance();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to initialize FirebaseApp");
        }
    }
}
